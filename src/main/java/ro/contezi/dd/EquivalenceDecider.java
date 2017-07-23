package ro.contezi.dd;

import ro.contezi.dd.cards.Card;

@FunctionalInterface
public interface EquivalenceDecider {
    boolean areEquivalent(Card<?> card, Card<?> anotherCard); 
}
