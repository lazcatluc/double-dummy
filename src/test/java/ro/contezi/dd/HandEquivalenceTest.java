package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import ro.contezi.dd.cards.Spade;

public class HandEquivalenceTest {
    @Test
    public void computesHandValueBasedOnCards() throws Exception {
        Hand hand = new Hand(
                Arrays.asList(Arrays.asList(new Spade(2)),
                              Arrays.asList(new Spade(5)),
                              Arrays.asList(new Spade(4)),
                              Arrays.asList(new Spade(3))
                             ));
        assertThat(hand.getHandValue()).isEqualTo(Arrays.asList(
            Arrays.asList(Arrays.asList(2), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), 
            Arrays.asList(Arrays.asList(5), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Arrays.asList(Arrays.asList(4), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Arrays.asList(Arrays.asList(3), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Collections.emptyList(),
            Arrays.asList(0, 0),
            0));
    }
    
    @Test
    public void computesHandValueBasedOnCardsKeepingTrackOfPlayedCards() throws Exception {
        Hand hand = new Hand(
                Arrays.asList(Arrays.asList(new Spade(2), new Spade(6)),
                              Arrays.asList(new Spade(5), new Spade(9)),
                              Arrays.asList(new Spade(4), new Spade(8)),
                              Arrays.asList(new Spade(3), new Spade(7))
                             ),
                new HashSet<>(Arrays.asList(new Spade(2), new Spade(5), new Spade(4), new Spade(3))),
                Arrays.asList(0, 1),
                new Trick(Collections.emptyList()),
                1);
        assertThat(hand.getHandValue()).isEqualTo(Arrays.asList(
            Arrays.asList(Arrays.asList(2), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), 
            Arrays.asList(Arrays.asList(5), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Arrays.asList(Arrays.asList(4), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Arrays.asList(Arrays.asList(3), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
            Collections.emptyList(),
            Arrays.asList(0, 1),
            1));
    }
}
