package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import ro.contezi.dd.cards.Diamond;
import ro.contezi.dd.cards.Spade;

public class TrickTest {

    @Test
    public void findsWinnerAmongCardsInTheSameSuitAsTheFirstCard() {
        assertThat(new Trick(Arrays.asList(new Spade(2), new Diamond('A'), new Spade('Q'), new Spade(10)))
                .getWinner()).contains(new Spade('Q'));
    }

    @Test
    public void completeTrick() throws Exception {
        assertThat(new Trick(Arrays.asList(new Spade(2), new Diamond('A'), new Spade('Q'), new Spade(10)))
                .isComplete()).isTrue();
    }
    
    @Test
    public void incompleteTrick() throws Exception {
        assertThat(new Trick(Arrays.asList(new Spade(2), new Diamond('A'), new Spade(10)))
                .isComplete()).isFalse();
    }
}
