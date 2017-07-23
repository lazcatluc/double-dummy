package ro.contezi.dd;

import java.util.List;
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
    
    public boolean isComplete() {
        return cards.size() == 4;
    }
}
