package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class ReadHandTest {

    @Test
    public void readsHand() {
        String hand = "32.32.32.32.54.54.54.54.76.76.76.76.T8.T8.T8.T8";
        
        assertThat(new Hand(hand).getHandValue()).isEqualTo(Arrays.asList(
                Arrays.asList(Arrays.asList(3, 2), Arrays.asList(3, 2), Arrays.asList(3, 2), Arrays.asList(3, 2)), 
                Arrays.asList(Arrays.asList(5, 4), Arrays.asList(5, 4), Arrays.asList(5, 4), Arrays.asList(5, 4)),
                Arrays.asList(Arrays.asList(7, 6), Arrays.asList(7, 6), Arrays.asList(7, 6), Arrays.asList(7, 6)),
                Arrays.asList(Arrays.asList(10, 8), Arrays.asList(10, 8), Arrays.asList(10, 8), Arrays.asList(10, 8)),
                Collections.emptyList(),
                Arrays.asList(0, 0),
                0));   
    }
    

}
