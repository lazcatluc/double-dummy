package ro.contezi.dd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ro.contezi.dd.cards.Card;

public class TrickPlayer {
    private final Trick trick;
    private final List<Card<?>> playerCards;
    private final EquivalenceDecider equivalentCards;
    
    public TrickPlayer(Trick trick, List<Card<?>> playerCards, EquivalenceDecider equivalentCards) {
        this.trick = trick;
        this.playerCards = playerCards;
        this.equivalentCards = equivalentCards;
    }
    
    List<Card<?>> getContinuations() {
        return getContinuations(Collections.emptySet());
    }
    
    public List<Trick> getNextTricks() {
        return getNextTricks(Collections.emptySet());
    }
    
    public List<Trick> getNextTricks(Set<Card<?>> excludedCards) {
        List<Card<?>> continuations = getContinuations(excludedCards);
        List<Trick> newTricks = new ArrayList<>();
        addToTricksIfNotExcluded(newTricks, continuations.get(0), excludedCards);
        for (int i = 1; i < continuations.size(); i++) {
            if (equivalentCards.areEquivalent(continuations.get(i), continuations.get(i - 1))) {
                continue;
            }
            addToTricksIfNotExcluded(newTricks, continuations.get(i), excludedCards);
        }
        return newTricks;
    }
    
    private void addToTricksIfNotExcluded(List<Trick> newTricks, Card<?> card, Set<Card<?>> excluded) {
        if (!excluded.contains(card)) {
            newTricks.add(trick.add(card));
        }
    }

    List<Card<?>> getContinuations(Set<Card<?>> excludedCards) {
        Optional<List<Card<?>>> cardsInTheSameSuitAsFirstCardOfTrick = trick.getFirstCard().map(Card::getClass)
                .map(clazz -> playerCards.stream().filter(clazz::isInstance).filter(card -> !excludedCards.contains(card))
                        .collect(Collectors.toList()));
            if (cardsInTheSameSuitAsFirstCardOfTrick.isPresent() && !cardsInTheSameSuitAsFirstCardOfTrick.get().isEmpty()) {
                return cardsInTheSameSuitAsFirstCardOfTrick.get();
            }
            return playerCards.stream().filter(card -> !excludedCards.contains(card)).collect(Collectors.toList());
    }
}
