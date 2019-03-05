package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int size;
    
    // Feel free to add more fields and constants.
    
    public ArrayHeap() {
        this.heap = this.makeArrayOfT(2);
        this.size = 0;
        
    }
    
    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int val) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[val]);
    }
    
    /** 4i - 2
     *  4i - 1
     *  4i
     *  4i + 1
     */
    
    @Override
    public T removeMin() {
        if (this.size <= 0) {
            throw new EmptyContainerException();
        }
        T min = heap[1];
        T last = heap[this.size];
        heap[1] = last;
        heap[this.size] = null;
        int index = 1;
        int newIndex = removeMinHelper(index);
        while (newIndex != index) {
            index = newIndex;
            newIndex = removeMinHelper(index);
        }
        this.size--;
        return min;
    }
    
    private int removeMinHelper(int index) {
        T firstChild;
        T secondChild;
        T thirdChild;
        T fourthChild = null;
        T minChild;
        int minChildIndex;
        if (heap.length <= index + 1) {
            return index;
        }
        else if (heap.length < 4 * index - 2 || heap[4 * index - 2] == null) {
            return index;
        } else {
            firstChild = heap[4 * index - 2];
            minChild = firstChild;
            minChildIndex = 4 * index - 2;
            if (heap.length > 4 * index - 1 && heap[4 * index - 1] != null) {
                secondChild = heap[4 * index - 1];
                if (firstChild.compareTo(secondChild) > 0) {
                    minChild = secondChild;
                    minChildIndex = 4 * index - 1;
                }
                if (heap.length > 4 * index && heap[4 * index] != null) {
                    thirdChild = heap[4 * index];
                    if (minChild.compareTo(thirdChild) > 0) {
                        minChild = thirdChild;
                        minChildIndex = 4 * index;
                        fourthChild = thirdChild;
                    }
                    if (heap.length > 4 * index + 1 && heap[4 * index + 1] != null) {
                        fourthChild = heap[4 * index + 1];
                    }
                }
            }
            if (fourthChild != null) {
            	if (minChild.compareTo(fourthChild) > 0) {
                    minChild = fourthChild;
                    minChildIndex = 4 * index + 1;
                }
            }
            if (heap[index].compareTo(minChild) > 0) {
                heap[minChildIndex] = heap[index];
                heap[index] = minChild;
                index = minChildIndex;
            } else {
                return minChildIndex;
            }
        }
        return index;
    }
    
    public T peekMin() {
        if (this.size <= 0) {
            throw new EmptyContainerException();
        }
        return heap[1];
    }
    
    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        this.size++;
        if (heap.length <= size) { //2<=1, 2<=2,
            T[] temp = makeArrayOfT(size*2);
            for (int i = 1; i < heap.length; i++) {
                temp[i] = heap[i];
            }
            heap = temp;
        }
        //if this is the first element in the heap
        if (size - 1 == 0) {
            this.heap[this.size] = item;
        } else {
            boolean sorted = false; //to check if heap is in correct form
            int childIndex = size; //next available index to put element
            while (!sorted) { //while the newest element is not in the correct position
                int parentIndex; //index of the parent of the child to be initialized
                parentIndex = (childIndex + 2)/4; //index of the parent
                //if parent is greater than item to insert
                if (heap[parentIndex].compareTo(item) > 0) {
                    heap[childIndex] = heap[parentIndex]; //put parent in new position
                    heap[parentIndex] = item; //put new item in parent position
                    childIndex = parentIndex; //set the index of the child equal to the parent index
                    if (childIndex == 1) { //if child is at the top of heap then no more sorting necessary
                        sorted = true;
                    }
                } else { //if child is not less than parent no more sorting necessary
                    heap[childIndex] = item;
                    sorted = true;
                }
            }
        }
    }
    
    @Override
    public int size() {
        return this.size;
    }
}


