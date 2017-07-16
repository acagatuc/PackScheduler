package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A LinkedQueue is a queue of elements that functions as an LinkedList, using the
 * LinkedAbstractList functionality to implement the FIFO queue design
 * @author Samantha Ryan
 *
 * @param <E> Generic parameter of the LinkedQueue
 */
public class LinkedQueue<E> implements Queue<E> {
	private LinkedAbstractList<E> queue;

	/**
	 * Creates a new LinkedQueue object
	 * 
	 * @param capacity
	 *            capacity for this linked queue
	 */
	public LinkedQueue(int capacity) {
		queue = new LinkedAbstractList<E>(capacity);
	}

	@Override
	public void enqueue(E element) {
		queue.add(queue.size(), element);

	}

	@Override
	public E dequeue() {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		E oldVal = queue.remove(0);
		return oldVal;
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void setCapacity(int capacity) {
		queue.setCapacity(capacity);

	}

}
