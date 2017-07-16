/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class methods
 * @author Samantha Ryan
 *
 */
public class ScheduleTest {
	Schedule s;
	Course test1;
	Course test2;
	Course test3;
	Course test4;
	Course test5;
	Course test6;
	
	
	
	/**
	 * Sets up a basic schedule and creates several test courses 
	 */
	@Before
	public void setUp() {
		s = new Schedule();
		test1 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
		test2 = new Course("CSC216", "Intro to Java II", "001", 3, "doe", 10, "MWF", 1400, 1530);
		test3 = new Course("CSC226", "Discrete Math", "001", 3, "miller", 10, "TH", 1200, 1330);
		test4 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
		test5 = new Course("CSC316", "Data Structures", "001", 3, "thomas", 10, "MW", 1230, 1400);
		test6 = null;
 
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#Schedule()}.
	 */
	@Test
	public void testSchedule() {
		assertEquals("My Schedule", s.getTitle());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#addCourseToSchedule(edu.ncsu.csc216.pack_scheduler.course.Course)}.
	 */
	@Test
	public void testAddCourseToSchedule()  {
		s.addCourseToSchedule(test1);
		s.addCourseToSchedule(test2);
		s.addCourseToSchedule(test3);
		assertEquals(3, s.getSize());
		
		
		try { 
			s.addCourseToSchedule(test4);
		} catch (IllegalArgumentException e) {
			assertEquals(3, s.getSize());
		}
		
		try {
			s.addCourseToSchedule(test5);
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#removeCourseFromSchedule(edu.ncsu.csc216.pack_scheduler.course.Course)}.
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		s.addCourseToSchedule(test1);
		s.addCourseToSchedule(test2);
		s.addCourseToSchedule(test3);
		
		try {
			s.removeCourseFromSchedule(test5);
		} catch (NullPointerException e) {
			assertEquals(3, s.getSize());
		}
		
		s.removeCourseFromSchedule(test1);
		assertEquals(2, s.getSize());
		s.removeCourseFromSchedule(test3);
		assertEquals(1, s.getSize());
		s.removeCourseFromSchedule(test2);
		assertEquals(0, s.getSize());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#resetSchedule()}.
	 */
	@Test
	public void testResetSchedule() {
		s.addCourseToSchedule(test1);
		s.addCourseToSchedule(test2);
		s.addCourseToSchedule(test3);
		s.setTitle("New Schedule Title");
		
		assertEquals(3, s.getSize());
		assertEquals("New Schedule Title", s.getTitle());
		
		s.resetSchedule();
		assertEquals(0, s.getSize());
		assertEquals("My Schedule", s.getTitle());
	} 

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#getScheduledCourses()}.
	 */
	
	@Test
	public void testGetScheduledCourses()  {
		s.addCourseToSchedule(test1);
		s.addCourseToSchedule(test2);
		s.addCourseToSchedule(test3);
		String[][] checkScheduledCourses = s.getScheduledCourses();
		
		
		assertTrue(Arrays.equals(checkScheduledCourses[0], test1.getShortDisplayArray()));
		assertTrue(Arrays.equals(checkScheduledCourses[1], test2.getShortDisplayArray()));
		assertTrue(Arrays.equals(checkScheduledCourses[2], test3.getShortDisplayArray()));
	} 
 
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#setTitle(java.lang.String)}.
	 */
	@Test
	public void testSetTitle() {
		assertEquals("My Schedule", s.getTitle());
		s.setTitle("New Title");
		assertEquals("New Title", s.getTitle());
		
		
	}
	
	/**
	 * Test method for canAdd() method
	 */
	@Test
	public void testCanAdd() {
		assertTrue(s.canAdd(test1));
		s.addCourseToSchedule(test1);
		assertFalse(s.canAdd(test4));
		assertFalse(s.canAdd(test6));
	}

}
