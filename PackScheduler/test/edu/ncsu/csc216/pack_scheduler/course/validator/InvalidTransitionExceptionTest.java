package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests InvalidTransitionException
 * @author Samantha Ryan
 *
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Sets up the test cases for the InvalidTransitionException
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Sets up test cases
	}

	/**
	 * Tests an InvalidTransitionException thrown with a custom message
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Tests an InvalidTransitionException thrown with the default message
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ce.getMessage());
	}

}