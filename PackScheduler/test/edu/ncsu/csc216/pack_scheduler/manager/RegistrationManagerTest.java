package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the RegistrationManager methods
 * 
 * @author Samantha Ryan
 *
 */
public class RegistrationManagerTest {

	private RegistrationManager manager;
	// Test Courses //
	Course test1 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
	Course test2 = new Course("CSC216", "Intro to Java II", "001", 3, "doe", 10, "MWF", 1400, 1530);
	Course test3 = new Course("CSC226", "Discrete Math", "001", 3, "miller", 10, "TH", 1200, 1330);
	Course test4 = new Course("CSC116", "Intro to Java I", "001", 3, "smith", 10, "MWF", 1200, 1330);
	Course test5 = new Course("CSC316", "Data Structures", "001", 3, "thomas", 10, "MW", 1230, 1400);
	Course test6 = new Course("ART312", "Impressionists", "001", 3, "pauls", 10, "TH", 800, 930);
	Course test7 = new Course("HIS112", "Intro to Western Civilizations", "001", 3, "chrisl", 10, "TH", 1500, 1630);
	Course test8 = null;

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception
	 *             if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
		manager.logout();
	}

	/**
	 * Tests the getCourseCatalog() method.
	 */
	@Test
	public void testGetCourseCatalog() {
		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, manager.getCourseCatalog().getCourseCatalog().length);
	}

	/**
	 * Tests the getStudentDirectory() method.
	 */
	@Test
	public void testGetStudentDirectory() {
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, manager.getStudentDirectory().getStudentDirectory().length);
	}

	/**
	 * Tests the login() method.
	 */
	@Test
	public void testLogin() {
		// Test Registrar LogIn//
		manager.login("registrar", "Regi5tr@r");
		assertEquals("registrar", manager.getCurrentUser().getId());
		manager.logout();

		// Test Student LogIn
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.login("zking", "pw");
		assertEquals("zking", manager.getCurrentUser().getId());

		// Test Duplicate LogIn
		try {
			manager.login("daustin", "pw");
		} catch (IllegalArgumentException e) {
			assertEquals("registrar", manager.getCurrentUser().getId());
		}

		manager.logout();

		// Test Incorrect Password
		try {
			manager.login("daustin", "wrongPW");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser());
		}

		// Test NonExistant User
		try {
			manager.login("fakeStudent", "pw");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser());
		}

		// Test Registrar with Wrong Password
		try {
			manager.login("registrar", "wrongPW");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser());
		}
	}

	/**
	 * Tests the logout() method.
	 */
	@Test
	public void testLogout() {
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.login("zking", "pw");
		assertEquals("zking", manager.getCurrentUser().getId());
		manager.logout();
		assertNull(manager.getCurrentUser());

	}

	/**
	 * Tests the getCurrentUser() method.
	 */
	@Test
	public void testGetCurrentUser() {
		assertNull(manager.getCurrentUser());

		manager.login("registrar", "Regi5tr@r");
		assertTrue(manager.getCurrentUser().getId().equals("registrar"));
	}

	/**
	 * Tests the enrollStudentInCourse() method
	 */
	@Test
	public void testEnrollStudentInCourse1() {
		try {
			manager.enrollStudentInCourse(test1);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Illegal Action");
		}

		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.login("zking", "pw");
		manager.enrollStudentInCourse(test1);
		assertEquals(manager.getCurrentUser().getId(), "zking");

	}

	/**
	 * Tests the dropStudentInCourse() method
	 */
	@Test
	public void testDropStudentInCourse() {
		try {
			manager.dropStudentFromCourse(test1);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Illegal Action");
		}

		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.login("zking", "pw");
		manager.enrollStudentInCourse(test1);
		manager.dropStudentFromCourse(test1);
		assertEquals(manager.getCurrentUser().getId(), "zking");

	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length);
		assertEquals("CSC226", scheduleFrostArray[0][0]);
		assertEquals("001", scheduleFrostArray[0][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
		assertEquals("9", scheduleFrostArray[0][4]);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("8", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		assertFalse(
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(7, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC116", scheduleHicksArray[1][0]);
		assertEquals("003", scheduleHicksArray[1][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);

		assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length);
		assertEquals("CSC116", scheduleHicksArray[0][0]);
		assertEquals("003", scheduleHicksArray[0][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests addFacultyToCourse() method
	 */
	@Test
	public void testAddFacultyToCourse() { 
		FacultyDirectory directory = manager.getFacultyDirectory();
		directory.loadFacultyFromFile("test-files/faculty_records.txt");
		Faculty f1 = directory.getFacultyById("awitt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		catalog.addCourseToCatalog("HIS101", "Intro to World History", "001", 3, null, 10, "A");
		Course c = catalog.getCourseFromCatalog("HIS101", "001");
		try {
			manager.addFacultyToCourse(c, f1);
			fail();
		} catch (IllegalArgumentException e) {
			//
		}
 
		manager.login("registrar", "Regi5tr@r");
		assertTrue(manager.getCurrentUser().getId().equals("registrar"));

		manager.addFacultyToCourse(c, f1);

	}
	
	/**
	 * Tests the removeFacultyFromCourse() method
	 */
	@Test
	public void testRemoveFacultyToCourse() {
		FacultyDirectory directory = manager.getFacultyDirectory();
		directory.loadFacultyFromFile("test-files/faculty_records.txt");
		Faculty f1 = directory.getFacultyById("awitt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		
		Course c = catalog.getCourseFromCatalog("CSC226", "001");
		try {
			manager.removeFacultyFromCourse(c, f1);
			fail();
		} catch (IllegalArgumentException e) {
			//
		}
		manager.login("registrar", "Regi5tr@r");
		assertTrue(manager.getCurrentUser().getId().equals("registrar"));

		manager.removeFacultyFromCourse(c, f1);

	}

}