package ro.contezi.dd.cards;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SpadeTest {

    @Test
    public void readsIntValueSameAsCharacter() {
        assertThat(new Spade(2)).isEqualTo(new Spade('2'));
    }

}
