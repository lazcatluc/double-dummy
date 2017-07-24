package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.dd.cards.Spade;

public class NextHandTest {
    
    private List<Hand> nextHand;
    
    @Before
    public void setUp() {
        nextHand = new Hand(
                Arrays.asList(Arrays.asList(new Spade(2)),
                              Arrays.asList(new Spade(5)),
                              Arrays.asList(new Spade(4)),
                              Arrays.asList(new Spade(3))
                             )).nextHands();
    }

    @Test
    public void getsCurrentPlayerCards() {
        assertThat(nextHand.get(0).getCurrentPlayerCards())
            .isEqualTo(Arrays.asList(new Spade(5)));
    }
    
    @Test
    public void getsNextHandOnTheCurrentTrick() throws Exception {
        assertThat(nextHand.get(0).getCurrentTrick())
            .isEqualTo(new Trick(Arrays.asList(new Spade(2))));
    }

    @Test
    public void playIsUnique() throws Exception {
        assertThat(nextHand.size()).isEqualTo(1);
    }
    
    @Test
    public void computesWinnerWhenTrickIsComplete() throws Exception {
        Hand hand = completeTrickHand();
        
        assertThat(hand.getTricksWon()).isEqualTo(Arrays.asList(0, 1));
    }
    
    @Test
    public void resetsTrickWhenTrickIsComplete() throws Exception {
        Hand hand = completeTrickHand();
        
        assertThat(hand.getCurrentTrick()).isEqualTo(new Trick(Collections.emptyList()));
    }
    
    @Test
    public void setsCurrentPlayerToWinnerWhenTrickIsComplete() throws Exception {
        Hand hand = completeTrickHand();
        
        assertThat(hand.getCurrentPlayerCards()).isEqualTo(Arrays.asList(new Spade(5)));
    }

    protected Hand completeTrickHand() {
        return nextHand.get(0).nextHands().get(0).nextHands().get(0).nextHands().get(0);
    }
}
