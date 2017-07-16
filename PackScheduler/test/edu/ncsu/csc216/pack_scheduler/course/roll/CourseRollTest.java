package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the functionality of the CourseRoll class
 * 
 * @author Samantha Ryan
 *
 */
public class CourseRollTest {

	private CourseRoll cr;
	private Student s1 = new Student("Margaret", "Hamilton", "mhamilton", "mhamilton@ncsu.edu", "pw", 15);
	private Student s2 = new Student("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "pw", 15);
	private Student s3 = new Student("Alan", "Turing", "aturing", " aturing@ncsu.edu", "pw", 15);;
	private Student s4 = new Student("Marie", "Curie", "mcurie", "mcurie@ncsu.edu", "pw", 15);;
	private Student s5 = new Student("Nikola", "Tesla", "ntesla", "ntesla@ncsu.edu", "pw", 15);;
	private Student s6 = new Student("Bill", "Nye", "scienceguy", "scienceguy@ncsu.edu", "pw", 15);;
	private Student s7 = new Student("Carl", "Sagan", "csagan", "csagan@ncsu.edu", "pw", 15);;
	private Student s8 = new Student("Neil", "Degrass Tyson", "realdealneil", "realdealneal@ncsu.edu", "pw", 15);;
	private Student s9 = new Student("Bill", "Gates", "bgates", "bgates@ncsu.edu", "pw", 15);;
	private Student s10 = new Student("Ada", "Lovelace", "alovelace", "alovelace@ncsu.edu", "pw", 15);;

	private Student s11 = new Student("Sally", "Ride", "sride", "sride@ncsu.edu", "pw", 15);
	private Student s12 = new Student("John", "Glenn", "jglenn", "jglenn@ncsu.edu", "pw", 15);;
	private Student s13 = new Student("George", "Washington", "gwash", "gwash@ncsu.edu", "pw", 15);
	private Student s14 = new Student("Alexander", "Hamilton", "aham", "aham@ncsu.edu", "pw", 15);
	private Student s15 = new Student("Aaron", "Burr", "aburr", "aburr@ncsu.edu", "pw", 15);
	private Student s16 = new Student("Thomas", "Jefferson", "tjeffs", "tjeffs@ncsu.edu", "pw", 15);
	private Student s17 = new Student("Eliza", "Schuyler", "elizasch", "elizasch@ncsu.edu", "pw", 15);
	private Student s18 = new Student("Anjelica", "Schuyler", "aschuyler", "aschuyler@ncsu.edu", "pw", 15);
	private Student s19 = new Student("Peggy", "Schuyler", "pschuyler", "pschuyler@ncsu.edu", "pw", 15);
	private Student s20 = new Student("James", "Madison", "jmadison", "jmadison@ncsu.edu", "pw", 15);
	private Student s21 = new Student("Phillip", "Hamilton", "pham", "pham@ncsu.edu", "pw", 15);

	private Student s22 = new Student("Margaret", "Hamilton", "mhamilton", "mhamilton@ncsu.edu", "pw", 15);
	private Student s23 = null;
	private Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");

	/**
	 * Sets up a CourseRoll object with an enrollment cap of 10 Creates 13 test
	 * student objects
	 */
	@Before
	public void setUp() {
		cr = c.getCourseRoll();
		// new Student(String firstName, String lastName, String id, String
		// email, String hashPW, int maxCredits);

	}

	/**
	 * Tests the CourseRoll's constructor method
	 */
	@Test
	public void testCourseRoll() {
		CourseRoll cr1 = c.getCourseRoll();
		assertEquals(10, cr1.getEnrollmentCap());

		try {
			cr1 = new CourseRoll(c, 9);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(10, cr1.getEnrollmentCap());
		}
 
		try {
			cr1 = new CourseRoll(c, 251);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(10, cr1.getEnrollmentCap());
		}

	}

	/**
	 * Tests the setEnrollmentCap() method of the CourseRoll class
	 */
	@Test
	public void testSetEnrollmentCap() {
		cr.setEnrollmentCap(50);
		assertEquals(50, cr.getEnrollmentCap());

		try {
			cr.setEnrollmentCap(9);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(50, cr.getEnrollmentCap());
		}

		try {
			cr.setEnrollmentCap(251);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(50, cr.getEnrollmentCap());
		}
	}

	/**
	 * Tests the enroll() method of the CourseRoll class
	 */
	@Test
	public void testEnroll() {
		cr.enroll(s1);

		// Attempt to add a duplicate student
		try {
			cr.enroll(s22);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(9, cr.getOpenSeats());
		}

		// Fill the roll to capacity
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);

		// Test enrolling past capacity adds student to waitlist
		cr.enroll(s11);
		assertEquals(cr.getNumberOnWaitlist(), 1);

	}

	/**
	 * Tests that a full waitlist will throw an exception
	 */
	@Test
	public void testWaitlist() {
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);

		assertEquals(cr.getNumberOnWaitlist(), 0);
		
		cr.enroll(s11);
		cr.enroll(s12);
		cr.enroll(s13);
		cr.enroll(s14);
		cr.enroll(s15);
		cr.enroll(s16);
		cr.enroll(s17);
		cr.enroll(s18);
		cr.enroll(s19);
		cr.enroll(s20);
		assertEquals(cr.getNumberOnWaitlist(), 10);
		
		//Try to over fill the waitlist
		try {
			cr.enroll(s21);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(cr.getNumberOnWaitlist(), 10);
		}
	}

	/**
	 * Tests the drop() method of the CourseRoll class
	 */
	@Test
	public void testDrop() {
		try {
			cr.drop(s1);
		} catch (IllegalArgumentException e) {
			assertEquals(cr.getOpenSeats(), cr.getEnrollmentCap());
		}
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7); 
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);
		cr.enroll(s12);

		cr.drop(s1); 
		assertEquals(0, cr.getOpenSeats());

		cr.drop(s10);
		assertEquals(0, cr.getOpenSeats());

		cr.drop(s5);
		assertEquals(1, cr.getOpenSeats());
		
		try {
			cr.drop(s23);
		} catch (IllegalArgumentException e) {
			assertEquals(1, cr.getOpenSeats());
		}
		
		

	}

	/**
	 * Tests the canEnroll() method of the CourseRoll class
	 */
	@Test
	public void testCanEnroll() {
		assertTrue(cr.canEnroll(s1));
		cr.enroll(s1);

		assertFalse(cr.canEnroll(s22));

		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);

		assertFalse(cr.canEnroll(s11));
	}

}
