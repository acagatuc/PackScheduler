/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A generic LinkedList object that extends the AbstractSequentialList class
 * 
 * @author Samantha Ryan
 * @param <E> generic parameter 
 *
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	ListNode front;
	ListNode back;
	int size;

	/**
	 * Constructs a new empty LinkedList object
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null, front, null);
		front.next = back;
		size = 0;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		LinkedListIterator it = new LinkedListIterator(index);
		return it;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();

		}
		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		LinkedListIterator it = new LinkedListIterator(index);
		it.add(element);

	}

	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		LinkedListIterator it = new LinkedListIterator(index);
		
		E replaced = it.next(); 
		it.set(element);
		return replaced;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * The ListNode represents an element in the linkedList and has a previous
	 * element, a next element, and it's data
	 * 
	 * @author Samantha Ryan
	 *
	 */
	class ListNode {
		E data;
		ListNode next;
		ListNode prev;

		/**
		 * Constructs a new ListNode with an Element data and default null prev
		 * and next
		 * 
		 * @param data element of the node
		 */
		ListNode(E data) {
			this.data = data;
			next = null;
			prev = null;
		}
		
		
		/**
		 * Constructs a ListNode and defines the element, the next ListNode, and the previous listNode
		 * @param element element of the list node 
		 * @param prev previous listNode
		 * @param next next listNode
		 */
		ListNode(E element, ListNode prev, ListNode next) {
			data = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * The LinkedListIterator traverses the LinkedList acting as a cursor
	 * between the previous element's next node and the next element's previous
	 * node
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		ListNode previous;
		ListNode next;
		int previousIndex;
		int nextIndex;
		ListNode lastRetrieved;

		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			previous = front;
			next = previous.next;
			previousIndex = index - 1;
			nextIndex = index;

			if (index != 0) {
				for (int i = 0; i < index; i++) {
					previous = next;
					next = next.next;

				}  
			}
			 
			lastRetrieved = null;

		}

		@Override
		public boolean hasNext() {
			return (next.data != null);
		}

		@Override
		public E next() {
			if (next.data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			E returnMe = next.data;
			previousIndex++;
            nextIndex++;
			next = next.next; 
			return returnMe;
		}

		@Override
		public boolean hasPrevious() {
			if (previous.data == null) {
				return false;
			}
			return true;
		}

		@Override
		public E previous() {
			if (previous.data == null) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			previousIndex--;
            nextIndex--;
			previous = previous.prev;
			return previous.data;
		}

		@Override
		public int nextIndex() {
			if (next == null) {
				return size; 
			}
			return nextIndex - 1;
		}

		@Override
		public int previousIndex() {			
			if (previous == null) {
                return -1;
            }
            return previousIndex;
		}

		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.prev.next = next;
			next = next.next;

			size--;

		}

		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}

			lastRetrieved.data = e;

		}

		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			ListNode newNode = new ListNode(e);
			next.prev = newNode;
			newNode.next = next;
			newNode.prev = previous;

			previous.next = newNode;
 
			size++;
			lastRetrieved = null;
		}
		
		 
	}
	

}
