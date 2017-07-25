package ro.contezi.dd;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import ro.contezi.dd.cards.Card;

public class Honors implements HonorValue {

    private final double[] topHonors;
    
    public Honors(double[] topHonors) {
        this.topHonors = topHonors;
    }
    
    @Override
    public double getHonorValue(List<Card<?>> cards, Set<Card<?>> excludedCards) {
        return cards.stream().mapToDouble(
                card -> computeHonorValue(card, excludedCards)).sum();
    }

    private double computeHonorValue(Card<?> card, Set<Card<?>> excludedCards) {
        if (excludedCards.contains(card)) {
            return 0;
        }
        int higherCardsLeftInSuit = 
                (int)IntStream.range(card.getValue() + 1, Card.max() + 1)
                .mapToObj(card::getCardOfTheSameSuit)
                .filter(higherCard -> !excludedCards.contains(higherCard))
                .count();
        if (higherCardsLeftInSuit >= topHonors.length) {
            return 0;
        }
        return topHonors[higherCardsLeftInSuit];
    }

}
