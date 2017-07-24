package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

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
}
