package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.dd.cards.Heart;
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

    @Test
    public void heart4And3areEquivalent() {
        assertThat(excludedCardsFromBetween.areEquivalent(new Heart(4), new Heart(3))).isTrue();
    }
    
    @Test
    public void heart5And3areNotEquivalent() {
        assertThat(excludedCardsFromBetween.areEquivalent(new Heart(5), new Heart(3))).isFalse();
    }
    
    @Test
    public void spade5And8areNotEquivalent() {
        assertThat(excludedCardsFromBetween.areEquivalent(new Spade(5), new Spade(8))).isFalse();
    }
}
