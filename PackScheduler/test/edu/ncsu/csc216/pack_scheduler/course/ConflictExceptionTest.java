package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests the functionality of the ConflictException class
 * @author Samantha Ryan
 *
 */
public class ConflictExceptionTest {

	/**
	 * Sets up the test cases for the ConflictException
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Sets up test cases
	}

	/**
	 * Tests a ConflictException thrown with a custom message
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Tests a ConflictException thrown with the default message
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
	    assertEquals("Schedule conflict.", ce.getMessage());
	}

}