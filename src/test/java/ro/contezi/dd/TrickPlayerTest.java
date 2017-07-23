package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import ro.contezi.dd.cards.Card;
import ro.contezi.dd.cards.Club;
import ro.contezi.dd.cards.Diamond;
import ro.contezi.dd.cards.Heart;
import ro.contezi.dd.cards.Spade;

public class TrickPlayerTest {

    @Test
    public void getsSameSuitContinuations() {
        Trick trick = new Trick(Arrays.asList(new Spade(2)));
        List<Card<?>> playerCards = Arrays.asList(new Spade(3), new Heart(4), new Spade(5));
        
        assertThat(new TrickPlayer(trick, playerCards, (c1, c2) -> false).getContinuations())
            .isEqualTo(new HashSet<>(Arrays.asList(new Spade(3), new Spade(5))));
    }

    @Test
    public void getsAllContinuationsWhenNoSpades() {
        Trick trick = new Trick(Arrays.asList(new Spade(2)));
        List<Card<?>> playerCards = Arrays.asList(new Diamond(3), new Heart(4), new Club(5));
        
        assertThat(new TrickPlayer(trick, playerCards, (c1, c2) -> false).getContinuations())
            .isEqualTo(new HashSet<>(Arrays.asList(new Diamond(3), new Heart(4), new Club(5))));
    }
    
    @Test
    public void getsAllContinuationsOnInitialLead() {
        Trick trick = new Trick(Collections.emptyList());
        List<Card<?>> playerCards = Arrays.asList(new Diamond(3), new Heart(4), new Club(5));
        
        assertThat(new TrickPlayer(trick, playerCards, (c1, c2) -> false).getContinuations())
            .isEqualTo(new HashSet<>(Arrays.asList(new Diamond(3), new Heart(4), new Club(5))));
    }
}
