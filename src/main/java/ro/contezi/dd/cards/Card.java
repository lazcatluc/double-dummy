package ro.contezi.dd.cards;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card<T extends Card<T>> implements Comparable<Card<?>> {

    private static final Map<Character, Integer> HONORS;
    
    static {
        Map<Character, Integer> honors = new HashMap<>();
        honors.put('T', 10);
        honors.put('J', 11);
        honors.put('Q', 12);
        honors.put('K', 13);
        honors.put('A', 14);
        HONORS = Collections.unmodifiableMap(honors);
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
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
}
