package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.util.Collections;

import org.junit.Test;

import ro.contezi.dd.cards.Spade;

public class HonorsTest {
    @Test
    public void computesHonorsWithInitialValues() throws Exception {
        assertThat(new Honors(new double[] { 1 }).getHonorValue(Collections.singletonList(new Spade('A')),
                Collections.emptySet())).isCloseTo(1, offset(0.01));
    }
}
