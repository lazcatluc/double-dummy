package ro.contezi.dd.cards;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DiamondTest {

    @Test
    public void aceBetterThanNine() {
        assertThat(new Diamond('A')).isGreaterThan(new Diamond(9));
    }

}
