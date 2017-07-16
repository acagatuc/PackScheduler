package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests CourseCatalog
 * 
 * @author Samantha Ryan
 *
 */
public class CourseCatalogTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	/** Test course name */
	private static final String NAME = "CSC116";
	/** Test course section */
	private static final String SECTION = "003";
	/** Test course title */
	private static final String TITLE = "Intro to Programming - Java";
	/** Test course credits */
	private static final int CREDITS = 3;
	/** Tests course instructor ID */
	private static final String INSTRUCTOR_ID = "spbalik";
	/** Tests course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Test course start time */
	private static final int START_TIME = 1250;
	/** Tests course end time */
	private static final int END_TIME = 1440;
	/** Test course meeting string */
	private static final String MEETING_STRING = "MW 12:50PM-2:40PM";
	/** Enrollment Cap */
	private static final int ENROLLMENT_CAP = 10;

	/**
	 * Resets course_records.txt for use in other tests
	 * 
	 * @throws Exception
	 *             if something fails during setup
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath); 
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog().
	 */
	@Test
	public void testCourseCatalog() {
		// Test that the CourseCatalog is initialized to an empty list
		CourseCatalog cc = new CourseCatalog();
		assertFalse(cc.removeCourseFromCatalog("CSC216", "001"));
		assertEquals(0, cc.getCourseCatalog().length);
	}

	/**
	 * Tests newCourseCatalog().
	 */
	@Test
	public void testNewCourseCatalog() {
		// Test that if there are courses in the catalog, they are removed after
		// calling newCourseCatalog().
		CourseCatalog cd = new CourseCatalog();

		cd.loadCoursesFromFile(validTestFile);
		assertEquals(3, cd.getCourseCatalog().length);

		cd.newCourseCatalog();
		assertEquals(0, cd.getCourseCatalog().length);
	}

	/**
	 * Tests loadCoursesFromFile().
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog cd = new CourseCatalog();

		cd.loadCoursesFromFile(validTestFile);
		assertEquals(3, cd.getCourseCatalog().length);

		CourseCatalog cdFail = new CourseCatalog();
		try {
			cdFail.loadCoursesFromFile(invalidTestFile);
		} catch (IllegalArgumentException e) {
			assertEquals(0, cdFail.getCourseCatalog().length);
		}
	}

	/**
	 * Tests addCourseToCatalog()
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog cd = new CourseCatalog();

		// Test valid course
		cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String[][] courseCatalog = cd.getCourseCatalog();
		assertEquals(1, cd.getCourseCatalog().length);
		assertEquals(NAME, courseCatalog[0][0]);
		assertEquals(SECTION, courseCatalog[0][1]);
		assertEquals(TITLE, courseCatalog[0][2]);
		assertEquals(MEETING_STRING, courseCatalog[0][3]);

		cd = new CourseCatalog();
		cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		assertEquals(1, cd.getCourseCatalog().length);

		// Test invalid courses//
		cd = new CourseCatalog(); 
		try {
			cd.addCourseToCatalog(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length); 
		}
		try {
			cd.addCourseToCatalog("", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, null, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, "", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, "", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, null, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, 0, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, 21, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, null, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "", START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 0, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 1530, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}
		try {
			cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 1200);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cd.getCourseCatalog().length);
		}

	}

	/**
	 * Test removeCourseFromCatalog()
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog cd = new CourseCatalog();

		cd.loadCoursesFromFile(validTestFile);
		assertEquals(3, cd.getCourseCatalog().length);
		assertTrue(cd.removeCourseFromCatalog("CSC116", "003"));
		String[][] courseCatalog = cd.getCourseCatalog();
		assertEquals(2, courseCatalog.length);
	}
	 
	/**
	 * Test getCourseCatalog()
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cd = new CourseCatalog();
		cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		cd.addCourseToCatalog(NAME, TITLE, "004", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String[][] catalog = cd.getCourseCatalog();
		assertEquals(2, cd.getCourseCatalog().length);
		assertEquals(NAME, catalog[0][0]);
		assertEquals(SECTION, catalog[0][1]);
		assertEquals(TITLE, catalog[0][2]);
		assertEquals(MEETING_STRING, catalog[0][3]);
		
		assertEquals(NAME, catalog[1][0]);
		assertEquals("004", catalog[1][1]);
		assertEquals(TITLE, catalog[1][2]);
		assertEquals(MEETING_STRING, catalog[1][3]);
		
	}
	
	/**
	 * Test getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cd = new CourseCatalog();

		// Add courses
		cd.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP, MEETING_DAYS, 1330, 1445);
		cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		cd.addCourseToCatalog("CSC216", "Programming Concepts - Java", "601", 4, "jep", ENROLLMENT_CAP, "A");
		assertEquals(3, cd.getCourseCatalog().length);
	
		Course c = cd.getCourseFromCatalog(NAME, SECTION);
		assertTrue(c.getTitle().equals(TITLE));
		assertFalse(c.getMeetingDays().equals("TH"));
		
		
		c = cd.getCourseFromCatalog("CSC230", "001");
		assertNull(c);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog cd = new CourseCatalog();

		// Add courses
		cd.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP, MEETING_DAYS, 1330, 1445);
		cd.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		cd.addCourseToCatalog("CSC216", "Programming Concepts - Java", "601", 4, "jep", ENROLLMENT_CAP, "A");
		assertEquals(3, cd.getCourseCatalog().length);
		cd.saveCourseCatalog("test-files/actual_course_records.txt");
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");

		// Saves to invalid file name //
		try {
			cd.saveCourseCatalog("/home/sesmith5/actual_course_records.txt");

		} catch (IllegalArgumentException e) {
			assertEquals(3, cd.getCourseCatalog().length);
		}
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile
	 *            expected output
	 * @param actFile
	 *            actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}