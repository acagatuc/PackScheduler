package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Queues are a special, simple type of list where elements are added and removed using a FIFO methodology
 * @author Samantha Ryan
 *
 * @param <E> Generic parameter of the Queue
 */
public interface Queue<E> {

	/**
	 * enqueue adds an element to the end of the line If there is no room
	 * (Capacity has been reached), an IllegalArgumentException is thrown
	 * 
	 * @param element
	 *            element to be added to the queue
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the Queue If the queue is
	 * empty, a NoSuchElementException is thrown
	 * 
	 * @return element previously at the front of the queue
	 */
	E dequeue();

	/**
	 * Returns true if the queue is empty, false otherwise
	 * 
	 * @return true if the queue is empty, false if the queue is not empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the queue
	 * 
	 * @return the number of elements in teh queue
	 */
	int size();

	/**
	 * sets the queue's capacity If the actual parameter is negative or if it is
	 * less than the number of elements in the queue, an
	 * IllegalArgumentException is thrown
	 * 
	 * @param capacity
	 *            new capacity of the queue
	 */
	void setCapacity(int capacity);
}
