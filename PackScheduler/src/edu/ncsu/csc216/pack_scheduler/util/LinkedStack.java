/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A LinkedStack is a stack that is implemented using functionality from the
 * LinkedAbstractList class
 * 
 * @author Samantha Ryan
 *
 * @param <E>
 *            generic parameter
 */
public class LinkedStack<E> implements Stack<E> {
	private LinkedAbstractList<E> stack;

	/**
	 * Constructs a new LinkedStack object with a specific capacity
	 * 
	 * @param capacity
	 *            capacity of the LinkedStack
	 */
	public LinkedStack(int capacity) {
		stack = new LinkedAbstractList<E>(capacity);
	} 
  
	@Override
	public void push(E element) {
		stack.add(0, element);

	}

	@Override
	public E pop() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		E oldVal = stack.remove(0);
		return oldVal;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < stack.size()) {
			throw new IllegalArgumentException();
		}
		stack.setCapacity(capacity);
	}

}
