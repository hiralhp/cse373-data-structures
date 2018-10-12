// Hiral Patel and Christofer Conrad Forbes
package datastructures.concrete.dictionaries;

import java.util.Arrays;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;


/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int size;
    
    public static final int DEFAULT_CAPACITY = 100;
    
    // You're encouraged to add extra fields (and helper methods) though!
    
    public ArrayDictionary() {
        this(DEFAULT_CAPACITY);
    }
    
    public ArrayDictionary(int arraySize) {
        if (arraySize <= 0) {
            throw new IllegalArgumentException();
        }
        size = 0;
        pairs = this.makeArrayOfPairs(arraySize);
    }
    
    
    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
        
    }
    
    public V get(K key) {
        if (!this.containsKey(key)) {
            throw new NoSuchKeyException();
        }
        return pairs[pairIndex(key)].value;
    }
    
    public void put(K key, V value) {
        Pair<K, V> newPair = new Pair<K,V>(key, value);
        if (this.containsKey(key)) {
            pairs[pairIndex(key)] = newPair;
        } else {
            increaseCapacity(size + 1);
            pairs[size] = newPair;
            size++;
        }
    }
    
    public V remove(K key) {
        if (!this.containsKey(key)) {
            throw new NoSuchKeyException();
        }
        int index = pairIndex(key);
        V value = pairs[index].value;
        for (int i = index; i < size - 1; i++) {
            pairs[i] = pairs[i + 1];
        }
        size--;
        return value;
    }
    
    public boolean containsKey(K key) {
        return this.pairIndex(key) != -1;
    }
    
    public int size() {
        return size;
    }
    
    private int pairIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (pairs[i].key == key || pairs[i].key.equals(key)) {
                return i;
            }
        }
        return -1;
    }
    
    private void increaseCapacity(int capacity) {
        if (capacity > pairs.length) {
            int newCapacity = pairs.length * 2 + 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            pairs = Arrays.copyOf(pairs, newCapacity);
        }
        
    }
    
    private static class Pair<K, V> {
        public K key;
        public V value;
        
        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
