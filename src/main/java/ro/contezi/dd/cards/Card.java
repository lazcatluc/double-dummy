package ro.contezi.dd.cards;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card<T extends Card<T>> implements Comparable<Card<?>> {

    private static final Map<Character, Integer> HONORS;
    private static final Map<Integer, Serializable> REVERSE_HONORS;

    static {
        Map<Character, Integer> honors = new HashMap<>();
        Map<Integer, Character> reverseHonors = new HashMap<>();
        honors.put('T', 10);
        reverseHonors.put(10, 'T');
        honors.put('J', 11);
        reverseHonors.put(11, 'J');
        honors.put('Q', 12);
        reverseHonors.put(12, 'Q');
        honors.put('K', 13);
        reverseHonors.put(13, 'K');
        honors.put('A', 14);
        reverseHonors.put(14, 'A');
        HONORS = Collections.unmodifiableMap(honors);
        REVERSE_HONORS = Collections.unmodifiableMap(reverseHonors);
    }

    private final int value;

    public Card(int value) {
        this.value = value;
    }

    public Card(char value) {
        this.value = getCharacterValue(value);
    }

    @SuppressWarnings("unchecked")
    public T getCardOfTheSameSuit(int value) {
        try {
            return ((Constructor<T>) getClass().getConstructor(int.class)).newInstance(value);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static int getCharacterValue(Character character) {
        return HONORS.getOrDefault(character, character - '0');
    }

    @Override
    public int compareTo(Card<?> other) {
        return Integer.compare(value, getClass().cast(other).getValue());
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        return value == Card.class.cast(obj).getValue();
    }

    public final int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().substring(0, 1) + REVERSE_HONORS.getOrDefault(value, value);
    }
}
