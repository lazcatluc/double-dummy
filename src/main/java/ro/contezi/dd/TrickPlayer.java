package ro.contezi.dd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional<List<Card<?>>> cardsInTheSameSuitAsFirstCardOfTrick = trick.getFirstCard().map(Card::getClass)
            .map(clazz -> playerCards.stream().filter(clazz::isInstance).collect(Collectors.toList()));
        if (cardsInTheSameSuitAsFirstCardOfTrick.isPresent() && !cardsInTheSameSuitAsFirstCardOfTrick.get().isEmpty()) {
            return cardsInTheSameSuitAsFirstCardOfTrick.get();
        }
        return playerCards;
    }
    
    public List<Trick> getNextTricks() {
        List<Card<?>> continuations = getContinuations();
        List<Trick> newTricks = new ArrayList<>();
        newTricks.add(trick.add(continuations.get(0)));
        for (int i = 1; i < continuations.size(); i++) {
            if (equivalentCards.areEquivalent(continuations.get(i), continuations.get(i - 1))) {
                continue;
            }
            newTricks.add(trick.add(continuations.get(i)));
        }
        return newTricks;
    }
}
