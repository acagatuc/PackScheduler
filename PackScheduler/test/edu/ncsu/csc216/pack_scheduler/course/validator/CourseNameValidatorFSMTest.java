/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CourseNameValidatorFSM
 * @author Samantha Ryan
 *
 */
public class CourseNameValidatorFSMTest {
	CourseNameValidatorFSM cn;

	/**
	 * Sets up the test to create a new CourseNameValidatorFSM()
	 * @throws java.lang.Exception if CourseNamveValidatorFSM is unable to be constructed
	 */
	@Before
	public void setUp() throws Exception {
		cn = new CourseNameValidatorFSM();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM#isValid(java.lang.String)}.
	 * @throws InvalidTransitionException if the alphanumeric name is incorrect
	 */
	@Test
	public void testIsValidNonAlphanumeric() throws InvalidTransitionException {
		try {
			cn.isValid("$CS216");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
		}
		
		try {
			cn.isValid("CSC2!6");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
		}
		
		try {
			cn.isValid("CSC216.");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
		}
		
	}
	/**
	 * Tests the transition from the initial state (whether the name starts with a letter or a digit)
	 */
	@Test
	public void testIsValidStateInitial() {
		try {
			cn.isValid("216CSC");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name must start with a letter.");
		}
	}
	
	/**
	 * Tests whether the FSM transitions from state L to state LL and state D correctly
	 */
	@Test
	public void testIsValidStateL() {
		try {
			cn.isValid("CC214"); 	
		} catch (InvalidTransitionException e) {
			fail();
		}
		
	}
	
	/**
	 * Tests whether the FSM catches the error transitioning from state D to a letter state
	 */
	@Test
	public void testIsValidStateL2() {
		try {
			cn.isValid("C2C216");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests whether the state transitions correctly from LLLL
	 */
	@Test
	public void testIsValidStateLlll() {
		try {
			cn.isValid("CSCAA16");
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name cannot start with more than 4 letters.");
		}
	}
	
	/**
	 * Tests whether the state transitions correctly from LLLL
	 */
	@Test
	public void testIsValidStateLlll2() {
		try {
			cn.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}

	/**
	 * Tests whether the state transitons correctly from DD
	 */
	@Test
	public void testIsValidStateDd() {
		try {
			cn.isValid("CSC21A");
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name must have 3 digits.");
		}
	}
	
	/**
	 * Tests whether the state transitions correctly from DDD
	 */
	@Test
	public void testIsValidStateDdd() { 
		try {
			cn.isValid("CSC216A"); 
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests whether the state transitions correctly from DDD
	 */
	@Test
	public void testIsValidStateDDD2() {
		try {
			cn.isValid("CSC2161");
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only have 3 digits.");

		}
	}
	
	/**
	 * Tests whether the state transitions correctly from the suffix
	 */
	@Test
	public void testIsValidStateSuffix() {
		try {
			cn.isValid("CSC216AB");
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name can only have a 1 letter suffix.");

		}
	}
	
	/**
	 * Tests whether the state transitions correctly from the suffix
	 */
	@Test
	public void testIsValidStateSuffix2() {
		try {
			cn.isValid("CSC216A1");
		} catch (InvalidTransitionException e) {
			assertEquals(e.getMessage(), "Course name cannot contain digits after the suffix.");
		}
	}
		
		
		
	

}