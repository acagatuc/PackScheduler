package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Contains information about InvalidTransitionExceptions
 * @author Samantha Ryan
 *
 */
public class InvalidTransitionException extends Exception {
	
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an InvalidTransitionException using a custom String message
	 * @param message to be displayed as part of an InvalidTransitionException throw
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructs an InvalidTransitionExcpetion with the default error message
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}
	
}



