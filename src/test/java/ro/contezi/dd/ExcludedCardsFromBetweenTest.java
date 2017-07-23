package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.dd.cards.Spade;

public class ExcludedCardsFromBetweenTest {
    
    private ExcludedCardsFromBetween excludedCardsFromBetween;
    
    @Before
    public void setUp() {
        excludedCardsFromBetween = new ExcludedCardsFromBetween(new HashSet<>(Arrays.asList(
                new Spade(3), new Spade(4), new Spade(6))));
    }

    @Test
    public void spade2And5areEquivalent() {
        assertThat(excludedCardsFromBetween.areEquivalent(new Spade(2), new Spade(5))).isTrue();
    }

}
