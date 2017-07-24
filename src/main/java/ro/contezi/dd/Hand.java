package ro.contezi.dd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ro.contezi.dd.cards.Card;
import ro.contezi.dd.cards.Club;
import ro.contezi.dd.cards.Diamond;
import ro.contezi.dd.cards.Heart;
import ro.contezi.dd.cards.Spade;

public class Hand {
    private final List<List<Card<?>>> players;
    private final Set<Card<?>> playedCards;
    private final List<Integer> tricksWon;
    private final Trick currentTrick;
    private final int currentPlayer;
    
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
        }
        else {
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
        TrickPlayer trickPlayer = new TrickPlayer(currentTrick, getCurrentPlayerCards(), new ExcludedCardsFromBetween(playedCards));
        List<Trick> nextTricks = trickPlayer.getNextTricks(playedCards);
        int nextPlayer = currentPlayer + 1;
        if (nextPlayer == players.size()) {
            nextPlayer = 0;
        }
        List<Hand> nextHands = new ArrayList<>();
        for (Trick trick : nextTricks) {
            nextHands.add(new Hand(players, playedCards, tricksWon, trick, nextPlayer));
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

    public List<Object> getHandValue() {
        List<Object> handValue = new ArrayList<>();
        players.forEach(playerCards -> {
            List<List<Integer>> playerSuitedCards = new ArrayList<>();
            Arrays.asList(Spade.class, Heart.class, Diamond.class, Club.class).stream().forEach(suit -> {
                playerSuitedCards.add(playerCards.stream()
                                                 .filter(suit::isInstance)
                                                 .filter(card -> !playedCards.contains(card))
                                                 .map(this::getCardValue).collect(Collectors.toList()));
            });
            handValue.add(playerSuitedCards);
        });
        handValue.add(currentTrick.getCards());
        handValue.add(tricksWon);
        handValue.add(currentPlayer);
        return handValue;
    }
}
