package ro.contezi.dd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ro.contezi.dd.cards.Card;

public class Trick {
    private final List<Card<?>> cards;

    public Trick(List<Card<?>> cards) {
        this.cards = cards;
    }
    
    public Optional<Card<?>> getWinner() {
        return cards.stream().findFirst().map(Card::getClass)
                .flatMap(clazz -> cards.stream().filter(clazz::isInstance)
                                       .sorted((c1, c2) -> c2.compareTo(c1))
                                       .findFirst());
    }
    
    public Optional<Integer> getWinnerPosition() {
        return getWinner().map(cards::indexOf);
    }
    
    public Trick add(Card<?> card) {
        List<Card<?>> newTrick = new ArrayList<>(cards);
        newTrick.add(card);
        return new Trick(Collections.unmodifiableList(newTrick));
    }
    
    public Optional<Card<?>> getFirstCard() {
        return cards.stream().findFirst();
    }

    public List<Card<?>> getCards() {
        return cards;
    }
    
    public boolean isComplete() {
        return cards.size() == 4;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cards);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(this.cards, Trick.class.cast(obj).cards);
    }

    @Override
    public String toString() {
        return "Trick [cards=" + cards + "]";
    }

}
