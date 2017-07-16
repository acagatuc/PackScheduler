package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Stacks are simple, special types of lists where elements are added and removed from the top
 * @author Samantha Ryan
 *
 * @param <E> Generic parameter of the Stack
 */
public interface Stack<E> {

	/**
	 * Adds the element to the top of the stack If there is no room (capacity
	 * has been reached), an IllegalArgumentException is thrown
	 * 
	 * @param element
	 *            element to add
	 */
	void push(E element);

	/**
	 * Removes and returns the element at the top of the stack If the stack is
	 * empty, an EmptyStackException() is thrown
	 * 
	 * @return element from the top of the list
	 */
	E pop();

	/**
	 * Returns true if the stack is empty
	 * 
	 * @return true if the stack is empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the stack
	 * 
	 * @return the number of elements in the stack
	 */
	int size();

	/**
	 * Sets the stack's capacity f the actual parameter is negative or if it is
	 * less than the number of elements in the stack, an
	 * IllegalArgumentException is thrown
	 * 
	 * @param capacity
	 *            capacity of the stack
	 */
	void setCapacity(int capacity);
}
