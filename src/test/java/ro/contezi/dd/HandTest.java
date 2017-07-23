package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import ro.contezi.dd.cards.Spade;

public class HandTest {

    @Test
    public void getsCurrentPlayerCards() {
        assertThat(new Hand(Arrays.asList(Arrays.asList(new Spade(1)))).getCurrentPlayerCards())
            .isEqualTo(Arrays.asList(new Spade(1)));
    }

}
