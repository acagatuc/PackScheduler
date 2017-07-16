package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Activity Class
 * 
 * @author Samantha Ryan
 *
 */
public class ActivityTest {

	/** Activity name */
	private static final String NAME = "CSC216";
	/** Activity title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Activity section */
	private static final String SECTION = "001";
	/** Activity credits */
	private static final int CREDITS = 4;
	/** Activity instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Activity meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Activity start time */
	private static final int START_TIME = 1330;
	/** Activity end time */
	private static final int END_TIME = 1445;
	/** Enrollment Cap */
	private static final int ENROLLMENT_CAP = 50;

	/**
	 * Tests checkConflict()
	 */
	@Test
	public void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "TH", 1330, 1445);
		try {
			a1.checkConflict(a2); 
			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
					a2.getMeetingString());
		} catch (ConflictException e) {
			fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
		}

		// Update a1 with the same meeting days and a start time that overlaps
		// the end time of a2
		a1.setMeetingDays("TH");
		a1.setActivityTime(1445, 1530);
		try {
			a1.checkConflict(a2);
			fail(); // ConflictException should have been thrown, but was not.
		} catch (ConflictException e) {
			// Check that the internal state didn't change during method call.
			assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
			assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
		}
		try {
			a2.checkConflict(a1);
			fail(); // ConflictException should have been thrown, but was not.
		} catch (ConflictException e) {
			// Check that the internal state didn't change during method call.
			assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
			assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
		}
	}

	/**
	 * Test check conflict when there is an overlap in time in both directions
	 */
	@Test
	public void testCheckConflictOverlap() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1400, 1545);
		try {
			a1.checkConflict(a2);
			fail();
		} catch (ConflictException e) {
			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-3:45PM", a2.getMeetingString());
		}
		try {
			a2.checkConflict(a1);
			fail();
		} catch (ConflictException e) {
			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-3:45PM", a2.getMeetingString());
		}
 
	}

	/**
	 * Tests checkConflict() when there is a complete overlap in time and days
	 */
	@Test
	public void testCheckConflictStrongOverlap() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		try {
			a1.checkConflict(a2);
			fail();
		} catch (ConflictException e) {
			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		}

		try {
			a2.checkConflict(a1);
			fail();
		} catch (ConflictException e) {
			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		}
	}

	/**
	 * Tests checkConflict() when there is a conflict on a single day
	 */
	@Test
	public void testCheckConflictSingleDayOverlap() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MF", 1430, 1545);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		try {
			a1.checkConflict(a2);
			fail();
		} catch (ConflictException e) {
			assertEquals("MF 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		}

		try {
			a2.checkConflict(a1);
			fail();
		} catch (ConflictException e) {
			assertEquals("MF 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		}
	}

	/**
	 * Test No Conflict on Days in both directions
	 * 
	 */
	@Test
	public void testNoConflictDays() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "TF", 1430, 1545);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1330, 1445);
		try {
			a1.checkConflict(a2);
			assertEquals("TF 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		} catch (ConflictException e) {
			fail();
		}
		try {
			a2.checkConflict(a1);
			assertEquals("TF 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a2.getMeetingString());
		} catch (ConflictException e) {
			fail();
		}
	}

	/**
	 * Test No Conflict on Hours in both directions
	 * 
	 */
	@Test
	public void testNoConflictTime() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1430, 1545);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP , "MW", 1300, 1400);
		try {
			a1.checkConflict(a2);
			assertEquals("MW 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:00PM-2:00PM", a2.getMeetingString());
		} catch (ConflictException e) {
			fail();
		}
		try {
			a2.checkConflict(a1);
			assertEquals("MW 2:30PM-3:45PM", a1.getMeetingString());
			assertEquals("MW 1:00PM-2:00PM", a2.getMeetingString());
		} catch (ConflictException e) {
			fail();
		}
	}

	/**
	 * Test methods related to the Activity's title
	 */
	@Test
	public void testTitle() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		c1.setTitle("Java II: Electric Boogaloo");
		assertEquals("Java II: Electric Boogaloo", c1.getTitle());

		try {
			c1.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Java II: Electric Boogaloo", c1.getTitle());
		}

		try {
			c1.setTitle("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Java II: Electric Boogaloo", c1.getTitle());
		}

	}

	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS,
				START_TIME, END_TIME);
		Activity c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , "TH", START_TIME, END_TIME);
		Activity c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, 830, END_TIME);
		Activity c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, 1400);

		// Test for the same hash code for the same values
		assertEquals(c1.hashCode(), c2.hashCode());

		// Test for each of the fields
		assertNotEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
		assertNotEquals(c1.hashCode(), c5.hashCode());
		assertNotEquals(c1.hashCode(), c6.hashCode());
		assertNotEquals(c1.hashCode(), c7.hashCode());
		assertNotEquals(c1.hashCode(), c8.hashCode());
		assertNotEquals(c1.hashCode(), c9.hashCode());
	}

	/**
	 * Test equals
	 */
	@Test
	public void testEquals() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c3 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, 1220, END_TIME);
		Activity c4 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, 1800);
		Activity c5 = new Course(NAME, "Workout", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , "TH", START_TIME, END_TIME);
		Activity c7 = null;


		assertTrue(c1.equals(c2));
		assertFalse(c1.equals(c3));
		assertFalse(c1.equals(c4));
		assertFalse(c1.equals(c5));
		assertFalse(c1.equals(c6));
		assertFalse(c1.equals(c7));

	}

	/**
	 * Test Activity Time
	 * 
	 */
	@Test
	public void testActivityTime() {
		Activity a1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		a1.setActivityTime(1200, 1300);
		assertEquals("MW 12:00PM-1:00PM", a1.getMeetingString());

		try {
			a1.setActivityTime(1300, 1200);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("MW 12:00PM-1:00PM", a1.getMeetingString());
		}

		try {
			a1.setActivityTime(0, 1200);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("MW 12:00PM-1:00PM", a1.getMeetingString());
		}

		try {
			a1.setActivityTime(1300, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("MW 12:00PM-1:00PM", a1.getMeetingString());
		}

		Activity a2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		try {
			a2.setActivityTime(1200, 1300);
			assertEquals("Arranged", a2.getMeetingString());
			assertEquals(0000, a2.getStartTime());
			assertEquals(0000, a2.getEndTime());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test getMeetingString()
	 */
	@Test
	public void testGetMeetingString() {
		Activity a1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, START_TIME, END_TIME);
		Activity a2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, 1145, END_TIME);
		Activity a3 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, 800, 900);
		Activity a4 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP , MEETING_DAYS, 845, 1045);

		assertEquals("MW 11:45AM-2:45PM", a2.getMeetingString());
		assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
		assertEquals("MW 8:00AM-9:00AM", a3.getMeetingString());
		assertEquals("MW 8:45AM-10:45AM", a4.getMeetingString());

	}

}