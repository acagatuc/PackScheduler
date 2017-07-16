package edu.ncsu.csc216.pack_scheduler.io;

import java.io.*;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads and writes Faculty Record information to and from files
 * 
 * @author Samantha Ryan
 *
 */ 
public class FacultyRecordIO {
	/** Maximum number of courses a faculty member may teach */
	public static final int MAX_COURSES = 3;
	/** Minimum number of courses a faculty memeber may teach */
	public static final int MIN_COURSES = 1;

	/**
	 * Reads the faculty records from a file and adds the non-duplicate to the
	 * Faculty Directory
	 * 
	 * @param filename
	 *            name of the file to read from
	 * @return faculty directory containing the faculty records
	 * @throws FileNotFoundException
	 *             if file cannot be read
	 * 
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		int index = 0;
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				String line = fileReader.nextLine();
				Faculty facultyMember = readFaculty(line);
				boolean duplicateFaculty = false;

				for (int i = 0; i < faculty.size(); i++) {
					User f = faculty.get(i);
					if (facultyMember.getId().equals(f.getId())) {
						duplicateFaculty = true;
					} 
				}  
				if (!duplicateFaculty) {
					faculty.add(index, facultyMember);
					index++;
				} 
			} catch (Exception e) {
				// Skip the line
			}
		}
		fileReader.close();
		return faculty;
	}

	/**
	 * Reads a faculty record from a single line from the readFacultyRecords
	 * file and creates a new FAculty object from this information
	 * 
	 * @param nextLine
	 *            line from file to read Faculty information from
	 * @return Faculty object based on record information
	 */
	private static Faculty readFaculty(String nextLine) throws IllegalArgumentException {
		Scanner lineReader = new Scanner(nextLine);
		lineReader.useDelimiter(",");
		String firstName = null;
		String lastName = null;
		String id = null; 
		String email = null;
		String password = null;
		int maxCourses = 0;
		try {
			if (!lineReader.hasNextInt()) {
				firstName = lineReader.next();
			}
			if (!lineReader.hasNextInt()) {
				lastName = lineReader.next();
			}
			if (!lineReader.hasNextInt()) {
				id = lineReader.next();
			}
			if (!lineReader.hasNextInt()) {
				email = lineReader.next();
			}
			if (!lineReader.hasNextInt()) {
				password = lineReader.next(); 
			}
			if (lineReader.hasNextInt()) {
				maxCourses = lineReader.nextInt();
			}
			lineReader.close();
			return new Faculty(firstName, lastName, id, email, password, maxCourses);
		} catch (Exception e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Writes the faculty records from the directory to a new file
	 * 
	 * @param fileName
	 *            new file to write faculty records to
	 * @param facultyDirectory
	 *            directory from which to pull information to write
	 * @throws IOException
	 *             if file cannot be written
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		try {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
		} catch (Exception e) {
			throw new IOException();
		}
		fileWriter.close();
	}
	

}
