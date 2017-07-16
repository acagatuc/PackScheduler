/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * An ArrayStack is a stack that is implemented using functionality from the ArrayList class
 * @author Samantha Ryan
 * 
 * @param <E> generic parameter
 *
 */
public class ArrayStack<E> implements Stack<E> {
	private ArrayList<E> stack;
	int capacity;

	/**
	 * Constructs a new ArrayStack by setting stack to a new empty ArrayList
	 * @param capacity capacity of the ArrayStack
	 */
	public ArrayStack(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		stack = new ArrayList<E>();
		this.capacity = capacity;
		setCapacity(capacity);
	}

	@Override
	public void push(E element) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		stack.add(0, element);
 
	}

	@Override
	public E pop() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		E oldElement = stack.remove(0);
		return oldElement;
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
		this.capacity = capacity;
		

	}  

}
