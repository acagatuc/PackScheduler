package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import java.security.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test cases to test the StudentRecordIO class
 * @author Stormy
 *
 */ 
public class StudentRecordIOTest {

	/** Valid Student Records */
	private final String validStudentRecordFile = "test-files/student_records.txt";
	/** Invalid Student Records */
	private final String invalidStudentRecordFile = "test-files/invalid_student_records.txt";
	/** Hashed password */
	private String hashPW;
	/** Algorithm for finding hashed password */
	private static final String HASH_ALGORITHM = "SHA-256";

	private final String validStudent1 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	private final String validStudent2 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	private final String validStudent3 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	private final String validStudent4 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	private final String validStudent5 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	private final String validStudent6  = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	private final String validStudent7 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	private final String validStudent8 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	private final String validStudent9 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	private final String validStudent10 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	
	private final String[] validStudents = {validStudent1, validStudent2, validStudent3, validStudent4, validStudent5, validStudent6, validStudent7, validStudent8, validStudent9, validStudent10};
	
	/**
	 * sets up the hashed passwrod based on the hash algorithm
	 */
	@Before
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = new String(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	 
	/**
	 * Checks the files being used in the read/write test cases 
	 * @param expFile expected file
	 * @param actFile actual file
	 */
	private void checkFiles(String expFile, String actFile) {
	    try {
	        Scanner expScanner = new Scanner(new FileInputStream(expFile));
	        Scanner actScanner = new Scanner(new FileInputStream(actFile));
	        
	        while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
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
	 * Tests the readStudentRecords() method wit a valid student record file
	 */
	@Test
	public void testReadValidStudentRecords() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validStudentRecordFile);
			assertEquals(10, students.size());

			assertEquals(validStudents[3], students.get(0).toString());
			assertEquals(validStudents[6], students.get(1).toString());
			assertEquals(validStudents[4], students.get(2).toString());
			assertEquals(validStudents[5], students.get(3).toString());
			assertEquals(validStudents[2], students.get(4).toString());
			assertEquals(validStudents[8], students.get(5).toString());
			assertEquals(validStudents[0], students.get(6).toString());
			assertEquals(validStudents[9], students.get(7).toString());
			assertEquals(validStudents[1], students.get(8).toString());
			assertEquals(validStudents[7], students.get(9).toString());
 
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validStudentRecordFile);
		}
	}
	
	/**
	 * Tests the readStudentRecords() method with an invlaid student record file
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords(invalidStudentRecordFile);
			
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) { 
			fail("Unexpected FileNotFoundException");
		}
				 
	} 
	
	/**
	 * Tests the writeStudentRecords() method, attempting to use a file that is not permitted
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
	    SortedList<Student> students = new SortedList<Student>();
	    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	    //Assumption that you are using a hash of "pw" stored in hashPW
	    
	    try { 
	        StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
	        fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
	    } catch (IOException e) {
	        assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
	        //The actual error message on Jenkins!
	    }
	} 
	 
	/**
	 * Tests the writeStudentRecords() method 
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		students.add(new Student("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", hashPW, 4));
		students.add(new Student("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", hashPW, 14));
		students.add(new Student("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk", hashPW, 18));
		students.add(new Student("Raymond", "Brennan", "rbrennan", "litora.torquent@pellentesquemassalobortis.ca", hashPW, 12));
		students.add(new Student("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", hashPW, 3));
		students.add(new Student("Lane", "Berg", "lberg", "sociis@non.org", hashPW, 14));
		students.add(new Student("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", hashPW, 17));
		students.add(new Student("Althea", "Hicks", "ahicks", "Phasellus.dapibus@luctusfelis.com", hashPW, 11));
		students.add(new Student("Dylan", "Nolan", "dnolan", "placerat.Cras.dictum@dictum.net", hashPW, 5));
	
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student record file");
		}
		
		checkFiles("test-files/expected_full_student_records.txt", "test-files/actual_student_records.txt");
	}
 
	
 
}
