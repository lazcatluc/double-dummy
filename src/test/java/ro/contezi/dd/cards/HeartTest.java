package ro.contezi.dd.cards;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HeartTest {

    @Test
    public void heartsAreNotClubs() {
        assertThat(new Heart(2)).isNotEqualTo(new Club(2));
    }

}
