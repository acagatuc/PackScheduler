package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests FacultyDirectory. 
 * @author Sarah Heckman
 */
public class FacultyDirectoryTest {
	  
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/not_a_file.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "fdent";
	/** Test email */
	private static final String EMAIL = "fdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_COURSES = 3;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		//Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are facultys in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory(); 
		assertEquals(0, fd.getFacultyDirectory().length);
	}
 
	/**
	 * Tests FacultyDirectory.loadFacultysFromFile().
	 */
	@Test
	public void testLoadFacultysFromFile() {
		FacultyDirectory fd = new FacultyDirectory();
				
		//Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		FacultyDirectory fdFail = new FacultyDirectory();
		//Test invalid file
		try {
			fdFail.loadFacultyFromFile(invalidTestFile);
		} catch (IllegalArgumentException e) {
			assertEquals(0, fdFail.getFacultyDirectory().length);
		}
	} 
	
	/**
	 * Tests getFacultyById().
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory fd = new FacultyDirectory();
		//Load in valid files//
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		Faculty s = fd.getFacultyById("kpatel");
		assertTrue(s.getFirstName().equals("Kevyn")); 
		assertTrue(s.getLastName().equals("Patel"));
		assertTrue(s.getId().equals("kpatel"));
		
		try {
			s = fd.getFacultyById("sryan2");
		} catch (IllegalArgumentException e){
			assertTrue(s.getId().equals("zking"));
		}
		
		FacultyDirectory x = new FacultyDirectory();
		try {
			x.getFacultyById("sryan2");
		} catch (IllegalArgumentException e) {
			//
		}
	}
	
 
	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Test valid Faculty
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
		
		//Test invalid Facultys//
		fd = new FacultyDirectory(); 
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd.getFacultyDirectory().length);
		}
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd.getFacultyDirectory().length);
		}
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd.getFacultyDirectory().length); 
		}
		 
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd.getFacultyDirectory().length);
		}
		
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "NewPassword", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd.getFacultyDirectory().length);
		}
		 
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertEquals(1, fd.getFacultyDirectory().length);
		}
		
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
			fd.addFaculty(FIRST_NAME, LAST_NAME, "NewID", EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertEquals(2, fd.getFacultyDirectory().length);
		}
		
		
	} 
 
	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
				
		//Add facultys and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		fd.removeFaculty("awitt");
 
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length); 
		assertEquals("ebriggs", facultyDirectory[4][2]); 
	
		fd.removeFaculty("kpatel");
		facultyDirectory = fd.getFacultyDirectory();
		assertEquals(6, facultyDirectory.length);
		//assertEquals("ebriggs", facultyDirectory[3][2]);
		
		fd.removeFaculty("lwalls");
		facultyDirectory = fd.getFacultyDirectory();
		assertEquals(5, facultyDirectory.length);
	}
  
	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Add a faculty
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		
		assertEquals(3, fd.getFacultyDirectory().length);
		
		
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
		
		//Saves to invalid file name //
		try {
			fd.saveFacultyDirectory("/home/sesmith5/actual_faculty_records.txt");

		} catch (IllegalArgumentException e){
			assertEquals(3, fd.getFacultyDirectory().length);
		}
	} 
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
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