package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
		this.size = 0;
	}
	// Adds the given item to the end of the list.
	// If the list is empty it creates a new one with
	// the given parameter being the first item.
	public void add(T item) {
		Node<T> newNode = new Node<T>(this.back, item, null);
		if (this.front == null) {
			this.front = newNode;
		} else {
			this.back.next = newNode;
		}
		this.back = newNode;
		this.size++;
	}
	
	// Removes the last item from the list.
	// Throws an EmptyContainerException if the size is 0.
	public T remove() {
		if (this.size == 0) {
			throw new EmptyContainerException();
		} else {
			return delete(this.size() - 1);
		}
	}
	
	// Returns the item with the given index.
	// Throws an IndexOutOfBoundsException if the index
	// is greater than or equal to size or less than 0.
	public T get(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		return nodeAtIndex(index).data;
	}
	
	// Sets the data of the node with the given
	// index to be the same as the item sent in.
	// Throws an IndexOutOfBoundsException if the index
	// is greater than or equal to size or less than 0.
	public void set(int index, T item) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> placeHolder = nodeAtIndex(index);
		if (placeHolder != null) {
			Node<T> replaceNode = new Node<T>(placeHolder.prev, item, placeHolder.next);
			if (index == 0) {
				this.front = replaceNode;
				if (this.size() != 1) {
					replaceNode.next.prev = replaceNode;
				}
			}
			if (index == this.size() - 1) {
				this.back = replaceNode;
				if (this.size() != 1) {
					replaceNode.prev.next = replaceNode;
				}
			}
			if (index > 0 && index < this.size() - 1) {
				replaceNode.prev.next = replaceNode;
				replaceNode.next.prev = replaceNode;
			}
		}
	}

	// Inserts a node with the given item at
	// the index sent in.
	public void insert(int index, T item) {
		if (index < 0 || index >= this.size() + 1) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> placeHolder = nodeAtIndex(index);
		if (placeHolder != null) {
			Node<T> replaceNode = new Node<T>(placeHolder.prev, item, placeHolder);
			if (index == 0) {
				this.front = replaceNode;
				replaceNode.next.prev = replaceNode;
			}
			if (index == this.size()) {
				this.add(item);
				this.size--;
			}
			if (index > 0 && index < this.size()) {
				placeHolder.prev.next = replaceNode;
				placeHolder.prev = replaceNode;
			}
		} else {
			this.front = new Node<T>(null, item, null);
			this.back = this.front;
		}
		this.size++;
	}

	// Deletes the node at the given index.
	// Throws an IndexOutOfBoundsException if the index
	// is greater than or equal to size or less than 0.
	public T delete(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> placeHolder = nodeAtIndex(index);
		if (placeHolder != null) {
			if (index == 0) {
				this.front = placeHolder.next;
			}
			if (index == this.size() - 1) {
				this.back = placeHolder.prev;
			}
			if (index > 0 && index < this.size() - 1) {
				placeHolder.prev.next = placeHolder.next;
				placeHolder.next.prev = placeHolder.prev;
			}
			this.size--;
		}
		return placeHolder.data;
	}

	// Returns the index of the node with the given item.
	// Returns -1 if the given item isn't in the list.
	public int indexOf(T item) {
		int index = 0;
		Node<T> placeHolder = this.front;
		while (placeHolder != null) {
			if (placeHolder.data != null && placeHolder.data.equals(item)) {
				return index;
			} else if (item == null && placeHolder.data == null) {
				return index;
			}
			placeHolder = placeHolder.next;
			index++;
		}
		return -1;
	}

	// Returns the size of the list.
	public int size() {
		return this.size;
	}

	// Returns whether or not the list contains the given item.
	public boolean contains(T other) {
		return indexOf(other) != -1;
	}

	// Returns the node at that index.
	private Node<T> nodeAtIndex(int index) {
		Node<T> placeHolder = this.front;
		if (index < this.size() / 2) {
			int stop = 0;
			while (stop != index && placeHolder != null) {
				placeHolder = placeHolder.next;
				stop++;
			}
		} else if (index != this.size()) {
			placeHolder = this.back;
			int stop = this.size() - 1;
			while (stop != index && placeHolder != null) {
				placeHolder = placeHolder.prev;
				stop--;
			}
		} else {
			placeHolder = this.back;
		}
		return placeHolder;
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

		// Feel free to add additional constructors or methods to this class.
	}

	private static class DoubleLinkedListIterator<T> implements Iterator<T> {
		// You should not need to change this field, or add any new fields.
		private Node<T> current;

		public DoubleLinkedListIterator(Node<T> current) {
			// You do not need to make any changes to this constructor.
			this.current = current;
		}

		/**
		 * Returns 'true' if the iterator still has elements to look at; returns 'false'
		 * otherwise.
		 */
		
		// Returns whether or not the list has a next element.
		public boolean hasNext() {
			if (this.current != null) {
				return this.current != null;
			}
			return false;
		}

		/**
		 * Returns the next item in the iteration and internally updates the iterator to
		 * advance one element forward.
		 *
		 * @throws NoSuchElementException
		 *             if we have reached the end of the iteration and there are no more
		 *             elements to look at.
		 */
		
		// Returns the next node in the list.
		// Throws a NoSuchElementException if there is no next element.
		public T next() {
			if (this.current == null) {
				throw new NoSuchElementException();
			}
			Node<T> placeHolder = this.current;
			this.current = this.current.next;
			return placeHolder.data;
		}
	}
}
