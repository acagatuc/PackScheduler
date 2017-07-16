/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A LinkedAbstractList is a list of elements consisting of two pieces of
 * information: the list data and the location of the next element in the list A
 * LinkedAbstractList also has a capacity, which can be altered
 * 
 * @author Samantha Ryan
 *
 * @param <E>
 *            generic element parameter
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	private int size;
	private int capacity;
	private ListNode front;
	private ListNode back;

	/**
	 * Constructs a new LinkedAbstractList with a defined capacity The Default
	 * front ListNode is null, and the size is 0 If the desired capacity is less
	 * than 0, an IllegalArgumentException is thrown
	 * 
	 * @param capacity
	 *            maximum capacity for this LinkedAbstractList
	 */
	public LinkedAbstractList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}

		this.front = null;
		this.size = 0;
		this.capacity = capacity;
		this.back = front;
		back = front;
	}

	/**
	 * Sets the LinkedAbstractList's capacity to a new capacity If the new
	 * capacity is less than zero, or is less than the list's pre-existing size,
	 * then an IllegalArgumentException is thrown
	 * 
	 * @param newCapacity
	 *            new capacity for the LinkedAbstractList
	 */
	public void setCapacity(int newCapacity) {

		if (newCapacity < 0 || newCapacity < this.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = newCapacity;

	}

	/**
	 * Finds the element at a particular "index" of the LinkedAbstractList
	 * 
	 * @param index
	 *            index position of element to return
	 * @return element in the LinkedAbstractList
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		if (front == null) {
			throw new NullPointerException();
		}

		if (index == 0) {
			return front.data;
		}
		ListNode find = front;
		for (int i = 0; i < index; i++) {
			find = find.next;
		}

		return find.data;
	}

	/**
	 * Sets a particular index position in the LinkedAbstractList to an element
	 * 
	 * @param index
	 *            position to set the new element
	 * @param element
	 *            element to set the index to in the LinkedAbstractList
	 * @return original element that was replaced
	 */
	public E set(int index, E element) {
		if (front == null) {
			throw new IndexOutOfBoundsException();
		}
		// Preliminary Checks//

		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}

		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		// Good, now we can start setting elements!
		// If the index is 0, just make a new ListNode for front with the
		// element and the next
		ListNode current = front;
		E oldVal;
		if (index == 0) {
			oldVal = current.data;
			front = new ListNode(element, front.next);

			return oldVal;
		}

		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		oldVal = current.data;
		current.data = element;

		return oldVal;
	}

	/**
	 * Adds an element to the list at a particular index position
	 * 
	 * If the size is already at the list's capacity, then an
	 * IllegalArgumentException is thrown
	 * 
	 * If the index is less than zero or larger than the list's size, then an
	 * IndexOutOfBounds exception is thrown
	 * 
	 * If the element to be added is null, then a NullPointerException is thrown
	 * 
	 * If the element to be added already exists in the list, then an
	 * IllegalArgumentException is thrown
	 * 
	 * @param index
	 *            position at which to add the element
	 * @param element
	 *            element to add to the list
	 */
	public void add(int index, E element) {
		// Check to see if there is room in the list
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}

		// Checks if index is out of bounds
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		// Check to see if added element is null
		if (element == null) {
			throw new NullPointerException();
		}

		// Check if element is a duplicate
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (front == null) {
			front = new ListNode(element);
			back = front;
		}

		if (index == 0) {
			front = new ListNode(element, front);
		} else if (front != null && index > 0) {
			ListNode traveler = front;
			while (traveler != null && index > 1) {
				traveler = traveler.next;
				index--;
			}
			if (traveler != null) {
				traveler.next = new ListNode(element, traveler.next);
			}
		}

		back = front;
		while (back.next != null) {
			back = back.next;
		}
		size++;

	}

	/**
	 * Removes the element at the index position in the LinkedAbstractList If
	 * the index is less than 0 or larger than the size of the list, then an
	 * IndexOutOfBoundsException is thrown
	 * 
	 * @param index
	 *            position of element to remove
	 * @return eleemnt removed
	 */
	public E remove(int index) {
		if (front == null) {
			throw new IndexOutOfBoundsException();
		}

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;
		ListNode previous = null;

		while (current != null && index > 0) {
			previous = current;
			current = current.next;
			index--;
		}
		if (current != null) {
			if (current == front) {
				front = front.next;
				size--;
			} else {
				previous.next = current.next;
				size--;
			}
			return current.data;
		}

		return null;

	}

	/**
	 * Returns the size (number of elements) of the list
	 */
	@Override
	public int size() {

		return size;
	}

	/**
	 * The ListNode is an element of the LinkedAbstractList, composed of an
	 * element data and the next node in the sequence
	 * 
	 * @author Samantha Ryan
	 *
	 */
	private class ListNode {
		E data;
		ListNode next;

		ListNode(E element) {
			data = element;
			next = null;
		}

		ListNode(E element, ListNode ln) {
			data = element;
			next = ln;
		}
	}

}
