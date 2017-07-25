package ro.contezi.dd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import ro.contezi.ab.MaximizingAlphaBeta;
import ro.contezi.ab.transposable.MaximizingTranspositionAlphaBeta;
import ro.contezi.ab.transposable.TranspositionAwareNode;

public class AlphaBetaDoubleDummyTest {
    private static final Logger LOGGER = LogManager.getLogger(AlphaBetaDoubleDummyTest.class);
    
    @Test
    public void solvesSimpleHand() throws Exception {
        String hand = "32.32.32.32.54.54.54.54.76.76.76.76.T8.T8.T8.T8";
        
        assertThat(new MaximizingTranspositionAlphaBeta(new TranspositionAwareNode(new Hand(hand)), 
                Integer.MAX_VALUE, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue())
                .isCloseTo(-8, offset(0.001));
    }
    
    @Test
    public void partiallySolvesComplicatedHandWithCache() throws Exception {
        String hand = "QT9.AQ432.Q542.J.AKJ6.KT865.T3.53.754..J876.QT9762.832.J97.AK9.AK84";
        
        TranspositionAwareNode node = new TranspositionAwareNode(new Hand(hand));
        for (int i = 1; i <= 20; i++) {
            LOGGER.info("Found value at depth " + i + ": " +
                new MaximizingTranspositionAlphaBeta(node, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue());
            LOGGER.info("Cache size: " + node.getCacheSize());
        }
    }
    
    @Test
    public void partiallySolvesComplicatedHand() throws Exception {
        String hand = "QT9.AQ432.Q542.J.AKJ6.KT865.T3.53.754..J876.QT9762.832.J97.AK9.AK84";
        
        Hand node = new Hand(hand);
        for (int i = 20; i <= 21; i++) {
            LOGGER.info("Found value at depth " + i + ": " +
                new MaximizingAlphaBeta(node, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue());
        }
        node = new Hand(hand);
        for (int i = 21; i <= 21; i++) {
            LOGGER.info("Found value at depth " + i + ": " +
                new MaximizingAlphaBeta(node, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue());
        }
    }
}
