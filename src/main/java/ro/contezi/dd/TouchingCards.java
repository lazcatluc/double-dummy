package ro.contezi.dd;

import ro.contezi.dd.cards.Card;

public class TouchingCards implements EquivalenceDecider {

    @Override
    public boolean areEquivalent(Card<?> card, Card<?> anotherCard) {
        return card.getClass().isInstance(anotherCard) && 
                Math.abs(card.getValue() - anotherCard.getValue()) <= 1;
    }

}
