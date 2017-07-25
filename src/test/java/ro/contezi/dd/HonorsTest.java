package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import ro.contezi.dd.cards.Club;
import ro.contezi.dd.cards.Heart;
import ro.contezi.dd.cards.Spade;

public class HonorsTest {
    @Test
    public void computesHonorsWithInitialValues() throws Exception {
        assertThat(new Honors(new double[] { 1 }).getHonorValue(Collections.singletonList(new Spade('A')),
                Collections.emptySet())).isCloseTo(1, offset(0.01));
    }
    
    @Test
    public void computesLowerHonorsWithInitialValues() throws Exception {
        assertThat(new Honors(new double[] { 4, 3, 2, 1 }).getHonorValue(
                Arrays.asList(new Spade('A'), new Heart('K'), new Club('J')),
                Collections.emptySet())).isCloseTo(8, offset(0.01));
    }
    
    @Test
    public void promotesToHonorsWhenCardsAreExcluded() throws Exception {
        assertThat(new Honors(new double[] { 4, 3, 2, 1 }).getHonorValue(
                Arrays.asList(new Spade('T')),
                Collections.singleton(new Spade('A')))).isCloseTo(1, offset(0.01));
    }
    
    @Test
    public void excludedCardsHaveZeroValue() throws Exception {
        assertThat(new Honors(new double[] { 4, 3, 2, 1 }).getHonorValue(
                Arrays.asList(new Spade('A')),
                Collections.singleton(new Spade('A')))).isCloseTo(0, offset(0.01));
    }
}
