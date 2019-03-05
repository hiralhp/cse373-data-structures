package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See ISet for more details on what each method is supposed to do.
 */
public class ChainedHashSet<T> implements ISet<T> {
    // This should be the only field you need
    private IDictionary<T, Boolean> map;
	
    
    public ChainedHashSet() {
        this.map = new ChainedHashDictionary<>();
    }
    /**
     * Adds the given item to the set.
     *
     * If the item already exists in the set, this method does nothing.
     */
    public void add(T item) {
        if (!contains(item)) {
            map.put(item, true);
        }
    }
    
    /**
     * Removes the given item from the set.
     *
     * @throws NoSuchElementException  if the set does not contain the given item
     */
    public void remove(T item) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        map.remove(item);
    }
    
    /**
     * Returns 'true' if the set contains this item and false otherwise.
     */
    public boolean contains(T item) {
        return map.containsKey(item);
    }
    
    /**
     * Returns the number of items contained within this set.
     */
    public int size() {
        return map.size();
    }
    
    /**
     * Returns all items contained within this set.
     */
    public Iterator<T> iterator() {
        return new SetIterator<>(this.map.iterator());
    }
    
    private static class SetIterator<T> implements Iterator<T> {
        // This should be the only field you need
        private Iterator<KVPair<T, Boolean>> iter;
        //added
       
        public SetIterator(Iterator<KVPair<T, Boolean>> iter) {
        		
            this.iter = iter;
            //added
            
        }
        
        @Override
        public boolean hasNext() {
        		return (this.iter.hasNext());//return(this.iter != null && this.iter.next() != null);

        }
        
        @Override
        public T next() {
        	if (iter.hasNext()) {
                return this.iter.next().getKey();
            }
            throw new NoSuchElementException();
            
        }
    }
}