package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Student object across all methods/ fields
 * 
 * @author Samantha Ryan
 *
 */
public class StudentTest {

	/** Default first name of Student */
	private static final String FIRST_NAME = "Margaret";
	/** Default last name of Student */
	private static final String LAST_NAME = "Hamilton";
	/** Default ID of Student */
	private static final String ID = "mhamilton";
	/** Default email of Student */
	private static final String EMAIL = "email@ncsu.edu";
	/** Default password of Student */
	private static final String PASSWORD = "password";
	/** Default max credits of Student */
	private static final int MAX_CREDITS = 18;
	
	//Test Courses //	
	Course test1 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
	Course test2 = new Course("CSC216", "Intro to Java II", "001", 3, "doe", 10, "MWF", 1400, 1530);
	Course test3 = new Course("CSC226", "Discrete Math", "001", 3, "miller", 10, "TH", 1200, 1330);
	Course test4 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
	Course test5 = new Course("CSC316", "Data Structures", "001", 3, "thomas", 10, "MW", 1230, 1400);
	Course test6 = new Course("ART312", "Impressionists", "001", 3, "pauls", 10, "TH", 800, 930);
	Course test7 = new Course("HIS112", "Intro to Western Civilizations", "001", 3, "chrisl", 10, "TH", 1500, 1630);
	Course test8 = null;

	/**
	 * Tests the Student creator across all fields
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		// Test Valid Student //
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertEquals(FIRST_NAME, s1.getFirstName());
		assertEquals(LAST_NAME, s1.getLastName());
		assertEquals(EMAIL, s1.getEmail());
		assertEquals(PASSWORD, s1.getPassword());
		assertEquals(MAX_CREDITS, s1.getMaxCredits());

		User s2 = null; 

		// First Name Fail//
		try {
			s2 = new Student(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		try {
			s2 = new Student("", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}
 
		// Last Name Fail//
		try {
			s2 = new Student(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// ID Fail//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}
		
		// ID Fail 2//
		try { 
			s2 = new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Email Fail//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Password Fail//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Max Credits Fail//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

	}

	/**
	 * Tests the Student creator passed with default max credits
	 */
	@Test 
	public void testStudentStringStringStringStringString() {
		// Test Valid Student//
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s1.getFirstName());
		assertEquals(LAST_NAME, s1.getLastName());
		assertEquals(ID, s1.getId());
		assertEquals(EMAIL, s1.getEmail());
		assertEquals(PASSWORD, s1.getPassword());
		assertEquals(MAX_CREDITS, s1.getMaxCredits());

		User s2 = null;

		// First Name Test//
		try {
			s2 = new Student(null, LAST_NAME, ID, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		try {
			s2 = new Student("", LAST_NAME, ID, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Last Name Test//
		try {
			s2 = new Student(FIRST_NAME, null, ID, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// ID Test//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Email Test//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, ID, null, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

		// Password Test//
		try {
			s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s2);
		}

	}

	/**
	 * Tests setFirstName
	 */
	@Test
	public void testSetFirstName() {
		// Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());

		}

		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());

		}

		// Test that a valid string changes only firstName //
		s.setFirstName("Mary");
		assertEquals("Mary", s.getFirstName());

	}

	/**
	 * Tests setLastName()
	 */
	@Test
	public void testSetLastName() {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());

		}

		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(LAST_NAME, s.getLastName());
		}

		s.setLastName("Smith");
		assertEquals("Smith", s.getLastName());

	}

	/**
	 * Tests setEmail
	 */
	@Test
	public void testSetEmail() {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, s.getEmail());
		}

		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, s.getEmail());
		}

		try {
			s.setEmail("email@ncsudotedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, s.getEmail());
		}

		try {
			s.setEmail("emailAtncsue.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, s.getEmail());
		}

		try {
			s.setEmail("my.email@ncsudotedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, s.getEmail());

		}
	}

	/**
	 * Tests setPassword()
	 */
	@Test
	public void testSetPassword() {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, s.getPassword());
		}

		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, s.getPassword());
		}
	}

	/**
	 * Tests setMaxCreidts();
	 */
	@Test
	public void testSetMaxCredits() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		try {
			s.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
	}
	
	/**
	 * Tests the canAdd() method
	 */
	@Test
	public void testCanAdd() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 12);
		
		assertTrue(s1.canAdd(test1));
		s1.getSchedule().addCourseToSchedule(test1);
		assertTrue(s1.canAdd(test2));
		s1.getSchedule().addCourseToSchedule(test2);
		assertTrue(s1.canAdd(test3));
		s1.getSchedule().addCourseToSchedule(test3);
		assertFalse(s1.canAdd(test4));
		assertFalse(s1.canAdd(test5));
		assertFalse(s1.canAdd(test8));
		assertTrue(s1.canAdd(test6));
		s1.getSchedule().addCourseToSchedule(test6);
		assertFalse(s1.canAdd(test7));
		
	}

	/**
	 * Tests hashCode generation
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s3 = new Student("Grace", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "Hopper", ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "newId", EMAIL, PASSWORD, MAX_CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "email@unc.edu", PASSWORD, MAX_CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "newPassword", MAX_CREDITS);
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);

		// Test equal hash values //
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test unequal hash values across all parameters //
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());

	}

	/**
	 * Tests equals()
	 */
	@Test
	public void testEqualsObject() {
		Object o1 = new String("Hello, World");
		
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s3 = new Student("Grace", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "Hopper", ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "newId", EMAIL, PASSWORD, MAX_CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "email@unc.edu", PASSWORD, MAX_CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "newPassword", MAX_CREDITS);
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);

		User s9 = null;
		
		//Test Object Get Class//
		assertNotEquals(s1, o1);
 
		// Test Equals itself//
		assertTrue(s1.equals(s1)); 

		// Test Equals for both directions //
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// Test Individual Fields//
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));

		// Test Equals against null//
		assertFalse(s1.equals(s9));

	}

	/**
	 * Tests toString()
	 */
	@Test
	public void testToString() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		String s2 = "Margaret,Hamilton,mhamilton,email@ncsu.edu,password,18";

		assertEquals(s2, s1.toString());

		User s3 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		assertEquals(s2, s3.toString());
	}
	
	/**
	 * Tests the compareTo() method to sort students alphabetically by last name, first name, and ID
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", PASSWORD, MAX_CREDITS);
		Student s3 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		
		Student s4 = new Student("Zenia", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student("Anna", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		Student s6 = new Student(FIRST_NAME, LAST_NAME, "mhamilton2", "mhamilton2@ncsu.edu", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, "mham", "mhamilton2@ncsu.edu", PASSWORD, MAX_CREDITS);
		
		Student s8 = null;
		

		assertEquals(-1, s1.compareTo(s2));
		assertEquals(0, s1.compareTo(s3));
		assertEquals(1, s2.compareTo(s1)); 
		assertEquals(-1, s1.compareTo(s4));
		assertEquals(1, s1.compareTo(s5));
		assertEquals(-1, s1.compareTo(s6));
		assertEquals(1, s1.compareTo(s7));
		
		try {
			s1.compareTo(s8);
			fail();
		} catch (NullPointerException e) {
			assertNull(s8);
		}
		
		
		
		 
	}

}
