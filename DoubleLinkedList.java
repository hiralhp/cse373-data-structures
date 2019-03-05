// Hiral Patel and Christofer Conrad Forbes

package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;


import java.util.Iterator;
import java.util.NoSuchElementException;
/**
* Note: For more info on the expected behavior of your methods, see
* the source code for IList.
*/
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;
    
    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
    }
    
    
    public void add(T item) {
        Node<T> temp = new Node<T>(item);
        if (front != null){ //if nothing in the list
            temp.prev = this.back;
            this.back.next = temp;
            this.back = temp;
        }
        else { //if the list contains items already
            this.front = temp;
            this.back = this.front;
            this.front.prev = null;
            this.front.next = null;
        }
        this.size++;
    }
    
    public T remove() {
        if(this.size == 0) { //if nothing in the list, invalid
            throw new EmptyContainerException();
        }
        
        Node<T> temp = this.back;
        
        if(this.size == 1){ //if list only has one element
            this.front = null;
            this.back = null;
        }
        else if(this.size == 2) { //if the list has 2 elements
            this.front.next = null;
            this.back = this.front;
        }
        else { //if the size of the list doesn't pass previous cases
            this.back.next = null;
            this.back = this.back.prev;
        }
        
        this.size--;
        return temp.data;
    }
    
    
    public T get(int index) {
        if(index < 0 || index >= this.size) { //invalid index
            throw new IndexOutOfBoundsException();
        }
        T value = this.front.data; //remains unchanged if index is at front
        if (index != 0 && index == this.size-1) { //if index is at end
            value = this.back.data;
        } 
        else if (index != 0){ //if index in between front and back
            Node<T> temp = this.front.next;
            for(int i = 1; i < this.size - 1; i++) {
                if (i == index) {
                    value = temp.data;
                    return value;
                }
                temp = temp.next;
            }
        }
        return value;
    }
    
    
    public void set(int index, T item) {
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.insert(index, item); //inserts at given index
        this.delete(index+1); //deletes the item previously at given index
    }
    
    
    public void insert(int index, T item) {
        if(index < 0 || index >= this.size + 1) {
            throw new IndexOutOfBoundsException();
        }
        
        if(index == this.size) { //if adding to the end, not replacing last element
            this.add(item); 
        } else {
            Node<T> temp = new Node<T>(item);
            if (index == 0) { //if adding to front
                this.front.prev = temp;
                temp.next = this.front;
                this.front = temp;
            }
            else if(index == 1) { //if index is the second from front
                Node<T> pointer = front.next;
                temp.next = pointer;
                front.next = temp;
            }
            else if (index == this.size - 1) { //if index is at the back
                temp.prev = back.prev;
                back.prev = temp;
            }
            else { //if index doesn't fall within upper four cases
                Node<T> pointer = front.next;
                int nextIndex = 2;
                while(index != nextIndex) {
                    pointer = pointer.next;
                    nextIndex++;
                }
                temp.next = pointer.next;
                pointer.next = temp;
            }
            this.size++;
        }
    }
    
    
    public T delete(int index) {
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        T value = this.get(index); //gets the value that is going to be deleted
        if(index == this.size-1) { //if index is at the end
            this.remove();
            
        } else if(index == 1) { //if index is the second value
            this.front.next = this.front.next.next;
            this.size--;
        }
        else if(index == 0) { //if index is at front
            this.front = this.front.next;
            this.front.prev = null;
            this.size--;
        }
        else if(index == this.size - 2) { //if index is second to last
        		this.back.prev = this.back.prev.prev;
        		this.size--;
        }
        else if(index > 1) { //removes value if the index doesn't pass the previous 4 cases
            Node<T> temp = this.front.next;
            int nextIndex = 2;
            while(nextIndex != index) {
                temp = temp.next;
                nextIndex++;
            }
            temp.next = temp.next.next;
            this.size--;
        }
        
        return value;
    }
    
    
    public int indexOf(T item) {
        if(item == null) { //if item is null 
            if(front == null) { //if item is at front
                return 0;
            }
            else if(back == null) { //if item is at back
                return this.size-1;
            }
            else { //if item is in between front and back
                Node<T> find = this.front.next;
                for(int i = 1; i < this.size-1; i++) {
                    if (find.data == null) {
                        return i;
                    } find = find.next;
                }
            }
        }
        else { //if item isn't null
            Iterator<T> iter = this.iterator();
            int index = 0;
            while(iter.hasNext()) {
                if(iter.next().equals(item)) {
                    return index;
                }
                index++;
            }
        }
        return -1; //returns -1 if not found
    }
    
    public int size() {
        return this.size;
    }
    
    
    public boolean contains(T other) {
        return (this.indexOf(other) != -1);
    }
    
    
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }
    
    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;
        
        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        
        public Node(E data) {
            this(null, data, null);
        }
    }
    
    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;
        
        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }
        
        /**
        * Returns 'true' if the iterator still has elements to look at;
        * returns 'false' otherwise.
        */
        public boolean hasNext() {
            return (this.current != null);
        }
        
        /**
        * Returns the next item in the iteration and internally updates the
        * iterator to advance one element forward.
        *
        * @throws NoSuchElementException if we have reached the end of the iteration and
        *         there are no more elements to look at.
        */
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            T thisVal = this.current.data;
            this.current = this.current.next;
            return thisVal;
        }
    }
}
