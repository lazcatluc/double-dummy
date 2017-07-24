package ro.contezi.dd;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ro.contezi.dd.cards.Card;

public class ExcludedCardsFromBetween implements EquivalenceDecider {
    
    private static final Logger LOGGER = LogManager.getLogger(ExcludedCardsFromBetween.class);
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
        try {
            @SuppressWarnings("rawtypes")
            Constructor<? extends Card> constructor = card.getClass().getConstructor(int.class);
            for (int i = Math.min(card.getValue(), anotherCard.getValue()) + 1; i < Math.max(card.getValue(), anotherCard.getValue()); i++) {
                Card<?> inBetweenCard = constructor.newInstance(i);
                if (!excludedCards.contains(inBetweenCard)) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        
    }

}
