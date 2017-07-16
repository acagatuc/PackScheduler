package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * An ArrayQueue is a queue of elements that functions as an array, using the
 * ArrayList functionality to implement the FIFO queue design
 * 
 * @author Samantha Ryan
 *
 * @param <E> Generic parameter of the ArraayQueue
 */
public class ArrayQueue<E> implements Queue<E> {
	private ArrayList<E> queue;
	int capacity;

	/**
	 * Constructs a new empty ArrayQueue
	 * 
	 * @param capacity
	 *            maximum capacity for this ArrayQueue
	 */
	public ArrayQueue(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		queue = new ArrayList<E>();
		setCapacity(capacity);
		this.capacity = capacity;
	}

	/**
	 * Adds an element to the back of the queue
	 * 
	 * @param element
	 *            element to be added to the queue
	 */
	@Override
	public void enqueue(E element) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (size() == this.capacity) {
			throw new IllegalArgumentException();
		}
		queue.add(queue.size(), element);

	}

	/**
	 * Removes an element from the front of the queue
	 * 
	 * @return element that was at the front of the queue
	 */
	@Override
	public E dequeue() {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		E oldVal = queue.remove(0);
		return oldVal;
	}

	/**
	 * Tells whether the queue is empty
	 * 
	 * @return true if the queue is empty, false if otherwise
	 */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Gets the size of the arrayQueue
	 * 
	 * @return the number of elements in the queue
	 */
	@Override
	public int size() {
		return queue.size();
	}

	/**
	 * Sets the capacity of this queue to a new value; if the new capacity is
	 * negative or if it is less than the number of elements currently in the
	 * queue, then an IlleglaArgumentException is thrown
	 * 
	 * @param capacity
	 *            new capacity of the queue
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < queue.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;

	}

}
