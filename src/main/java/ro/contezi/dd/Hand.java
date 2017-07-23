package ro.contezi.dd;

import java.util.Arrays;
import java.util.Collections;
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
    
    public List<Card<?>> getCurrentPlayerCards() {
        return players.get(currentPlayer);
    }
}
