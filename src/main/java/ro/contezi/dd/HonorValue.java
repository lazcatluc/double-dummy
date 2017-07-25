package ro.contezi.dd;

import java.util.List;
import java.util.Set;

import ro.contezi.dd.cards.Card;

@FunctionalInterface
public interface HonorValue {
    double getHonorValue(List<Card<?>> cards, Set<Card<?>> excludedCards);
}
