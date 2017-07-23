package ro.contezi.dd;

import java.util.HashSet;
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
    
    Set<Card<?>> getContinuations() {
        Optional<Set<Card<?>>> cardsInTheSameSuitAsFirstCardOfTrick = trick.getFirstCard().map(Card::getClass)
            .map(clazz -> playerCards.stream().filter(clazz::isInstance).collect(Collectors.toSet()));
        if (cardsInTheSameSuitAsFirstCardOfTrick.isPresent() && !cardsInTheSameSuitAsFirstCardOfTrick.get().isEmpty()) {
            return cardsInTheSameSuitAsFirstCardOfTrick.get();
        }
        return new HashSet<>(playerCards);
    }
}
