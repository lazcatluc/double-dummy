package ro.contezi.dd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro.contezi.dd.cards.Card;

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
    
    private Hand(List<List<Card<?>>> players, Set<Card<?>> playedCards, List<Integer> tricksWon, Trick currentTrick,
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
}
