package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests the methods in the FacultyRecordIO class
 * 
 * @author Samantha Ryan
 *
 */
public class FacultyRecordIOTest {

	/** Valid Faculty Records */
	private final String validFacultyRecordFile = "test-files/faculty_records.txt";
	/** Invalid Student Records */
	private final String invalidFacultyRecordFile = "test-files/invalid_faculty_records.txt";
	/** Hashed password */
	private String hashPW;
	/** Algorithm for finding hashed password */
	private static final String HASH_ALGORITHM = "SHA-256";

	private final String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	private final String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	private final String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	private final String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	private final String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	private final String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	private final String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	private final String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	private final String[] validFacultyMembers = { validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7, validFaculty8 };

	/**
	 * Sets up the hashed password based on the hash algorithm
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());
			for (int i = 0; i < validFacultyMembers.length; i++) {
				validFacultyMembers[i] = validFacultyMembers[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Checks the files being used in the read/write test cases
	 * 
	 * @param expFile
	 *            expected file
	 * @param actFile
	 *            actual file
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests the readFacultyRecords() method wit a valid student record file
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testReadValidStudentRecords() throws FileNotFoundException {
 
		LinkedList<Faculty> facultyFromFile = FacultyRecordIO.readFacultyRecords(validFacultyRecordFile);
		
		assertEquals(8, facultyFromFile.size());
	} 
 
	/**
	 * Tests the readFacultyRecords() method with an invalid faculty record
	 */
	@Test
	public void testReadInvalidFacultyRecords() {
		LinkedList<Faculty> facultyMembers;
		try {
			facultyMembers = FacultyRecordIO.readFacultyRecords(invalidFacultyRecordFile);
			assertEquals(0, facultyMembers.size());
		} catch (FileNotFoundException e) {
			fail();
		}
	}



	/**
	 * Tests the writeFacultyRecords() method
	 */
	@Test
	public void testWriteFacultyRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		faculty.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
		faculty.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
		} catch (IOException e) {
			fail("Cannot write to student record file");
		}

		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

}
