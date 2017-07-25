package ro.contezi.dd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ro.contezi.ab.ABNode;
import ro.contezi.dd.cards.Card;
import ro.contezi.dd.cards.Club;
import ro.contezi.dd.cards.Diamond;
import ro.contezi.dd.cards.Heart;
import ro.contezi.dd.cards.Spade;

public class Hand implements ABNode {
    private static final Logger LOGGER = LogManager.getLogger(Card.class);
    
    private static final List<Class<? extends Card<?>>> SUITS = Arrays.asList(Spade.class, Heart.class, Diamond.class, Club.class);
    private static final List<Function<Character, Card<?>>> SUIT_FACTORY = Arrays.asList(Spade::new, Heart::new, Diamond::new, Club::new);
    private static final int PLAYERS = 4;
    
    private final List<List<Card<?>>> players;
    private final Set<Card<?>> playedCards;
    private final List<Integer> tricksWon;
    private final Trick currentTrick;
    private final int currentPlayer;
    
    public Hand(String cards) {
        this(readCards(cards));
        players.forEach(LOGGER::info);
    }

    static List<List<Card<?>>> readCards(String cards) {
        String[] spl = cards.split("\\.");
        if (spl.length != PLAYERS * SUITS.size()) {
            throw new IllegalArgumentException(cards);
        }
        List<List<Card<?>>> players = new ArrayList<>();
        for (int i = 0; i < PLAYERS; i++) {
            List<Card<?>> player = new ArrayList<>();
            for (int j = 0; j < SUITS.size(); j++) {
                List<Card<?>> suit = new ArrayList<>();
                char[] playerSuitCards = spl[i * PLAYERS + j].toCharArray();
                for (char playerSuitCard : playerSuitCards) {
                    suit.add(SUIT_FACTORY.get(j).apply(playerSuitCard));
                }
                Collections.sort(suit);
                Collections.reverse(suit);
                player.addAll(suit);
            }
            players.add(Collections.unmodifiableList(player));
        }
        return Collections.unmodifiableList(players);
    }

    public Hand(List<List<Card<?>>> players) {
        this.players = players;
        this.playedCards = Collections.emptySet();
        this.tricksWon = Arrays.asList(0, 0);
        this.currentTrick = new Trick(Collections.emptyList());
        this.currentPlayer = 0;
    }

    Hand(List<List<Card<?>>> players, Set<Card<?>> playedCards, List<Integer> tricksWon, Trick currentTrick,
            int currentPlayer) {
        this.players = players;
        if (currentTrick.isComplete()) {
            Card<?> winner = currentTrick.getWinner().orElseThrow(IllegalStateException::new);
            this.currentTrick = new Trick(Collections.emptyList());
            int i = 0;
            while (!players.get(i).contains(winner)) {
                i++;
            }
            this.currentPlayer = i;
            Set<Card<?>> newPlayedCards = new HashSet<>(playedCards);
            newPlayedCards.addAll(currentTrick.getCards());
            this.playedCards = Collections.unmodifiableSet(newPlayedCards);
            List<Integer> newTricksWon = new ArrayList<>(tricksWon);
            newTricksWon.set(i % 2, newTricksWon.get(i % 2) + 1);
            this.tricksWon = Collections.unmodifiableList(newTricksWon);
        } else {
            this.playedCards = playedCards;
            this.currentTrick = currentTrick;
            this.tricksWon = tricksWon;
            this.currentPlayer = currentPlayer;
        }
    }

    public List<Card<?>> getCurrentPlayerCards() {
        return players.get(currentPlayer);
    }

    public Trick getCurrentTrick() {
        return currentTrick;
    }

    public List<Hand> nextHands() {
        LOGGER.debug("Expanding " + this);
        TrickPlayer trickPlayer = new TrickPlayer(currentTrick, getCurrentPlayerCards(),
                new ExcludedCardsFromBetween(playedCards));
        List<Trick> nextTricks = trickPlayer.getNextTricks(playedCards);
        LOGGER.debug("Found possible next tricks: " + nextTricks);
        int nextPlayer = currentPlayer + 1;
        if (nextPlayer == players.size()) {
            nextPlayer = 0;
        }
        List<Hand> nextHands = new ArrayList<>();
        for (Trick trick : nextTricks) {
            Hand nextHand = new Hand(players, playedCards, tricksWon, trick, nextPlayer);
            if (trick.isComplete() && Math.abs(nextHand.currentPlayer - this.currentPlayer) % 2 == 0 && !nextHand.isTerminal()) {
                nextHands.addAll(nextHand.nextHands());
            }
            else {
                nextHands.add(nextHand);
            }
        }
        return nextHands;
    }

    public List<Integer> getTricksWon() {
        return tricksWon;
    }

    private int getCardValue(Card<?> card) {
        int cardValue = card.getValue();
        for (int i = 2; i < card.getValue(); i++) {
            if (playedCards.contains(card.getCardOfTheSameSuit(i))) {
                cardValue--;
            }
        }
        return cardValue;
    }

    private List<Object> handValue;
    public List<Object> getHandValue() {
        if (handValue == null) {
            handValue = new ArrayList<>();
            players.forEach(playerCards -> {
                List<List<Integer>> playerSuitedCards = new ArrayList<>();
                SUITS.stream().forEach(suit -> {
                    playerSuitedCards
                            .add(playerCards.stream().filter(suit::isInstance).filter(card -> !playedCards.contains(card))
                                    .map(this::getCardValue).collect(Collectors.toList()));
                });
                handValue.add(playerSuitedCards);
            });
            handValue.add(currentTrick.getCards().stream().map(card -> card.getCardOfTheSameSuit(getCardValue(card)))
                    .collect(Collectors.toList()));
            handValue.add(tricksWon);
            handValue.add(currentPlayer);
        }
        return handValue;
    }

    @Override
    public int hashCode() {
        return getHandValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(this.getHandValue(), Hand.class.cast(obj).getHandValue());
    }

    @Override
    public double heuristicValue() {
        return tricksWon.get(0) - tricksWon.get(1);
    }

    @Override
    public boolean isTerminal() {
        return playedCards.size() == players.stream().mapToInt(List::size).sum();
    }

    @Override
    public Collection<? extends ABNode> children() {
        return nextHands();
    }

    @Override
    public String toString() {
        return "Hand [playedCards=" + playedCards + ", tricksWon=" + tricksWon
                + ", currentTrick=" + currentTrick + ", currentPlayer=" + currentPlayer + "]";
    }
    
}
