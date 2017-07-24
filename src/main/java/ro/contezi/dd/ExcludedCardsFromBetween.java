package ro.contezi.dd;

import java.util.Set;

import ro.contezi.dd.cards.Card;

public class ExcludedCardsFromBetween implements EquivalenceDecider {

    private final Set<Card<?>> excludedCards;

    public ExcludedCardsFromBetween(Set<Card<?>> excludedCards) {
        this.excludedCards = excludedCards;
    }

    @Override
    public boolean areEquivalent(Card<?> card, Card<?> anotherCard) {
        if (!card.getClass().isInstance(anotherCard)) {
            return false;
        }
        if (excludedCards.contains(card) && !excludedCards.contains(anotherCard)) {
            return false;
        }
        if (excludedCards.contains(anotherCard) && !excludedCards.contains(card)) {
            return false;
        }
        for (int i = Math.min(card.getValue(), anotherCard.getValue()) + 1; i < Math.max(card.getValue(),
                anotherCard.getValue()); i++) {
            if (!excludedCards.contains((Card<?>) card.getCardOfTheSameSuit(i))) {
                return false;
            }
        }
        return true;
    }

}
