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
    
    @Test
    public void sortsSuitsDecreasing() {
        String hand = "342.32.23.32.54.456.54.54.78.78.789.79.TQ.JK.TQ.AKQ";
        
        assertThat(new Hand(hand).getHandValue()).isEqualTo(Arrays.asList(
                Arrays.asList(Arrays.asList(4, 3, 2), Arrays.asList(3, 2), Arrays.asList(3, 2), Arrays.asList(3, 2)), 
                Arrays.asList(Arrays.asList(5, 4), Arrays.asList(6, 5, 4), Arrays.asList(5, 4), Arrays.asList(5, 4)),
                Arrays.asList(Arrays.asList(8, 7), Arrays.asList(8, 7), Arrays.asList(9, 8, 7), Arrays.asList(9, 7)),
                Arrays.asList(Arrays.asList(12, 10), Arrays.asList(13, 11), Arrays.asList(12, 10), Arrays.asList(14, 13, 12)),
                Collections.emptyList(),
                Arrays.asList(0, 0),
                0));   
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfIncorrectFormat() throws Exception {
        new Hand("foo-bar");
    }
}
