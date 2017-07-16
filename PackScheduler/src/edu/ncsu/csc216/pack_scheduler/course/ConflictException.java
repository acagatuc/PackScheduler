package edu.ncsu.csc216.pack_scheduler.course;
/**
 * Describes custom ConflictException 
 * @author Samantha Ryan
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a ConflictException using a custom String message
	 * @param message to be displayed as part of a ConflictException throw
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Constructs a ConflictExcpetion with the default error message
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
	
}
