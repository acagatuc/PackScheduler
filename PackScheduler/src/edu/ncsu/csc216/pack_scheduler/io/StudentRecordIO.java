package edu.ncsu.csc216.pack_scheduler.io;

import java.io.*;
import java.util.*;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Reads and writes Student Record information to and from files.
 * 
 * @author Samantha Ryan
 *
 */
public class StudentRecordIO {
	/** Max credits a student may take */
	public static final int MAX_CREDITS = 18;

	/**
	 * Reads the student records from a file and adds the non-duplicate students
	 * to the Student Directory
	 * 
	 * @param fileName
	 *            name of file to read from
	 * @return student directory containing Student records
	 * @throws FileNotFoundException
	 *             if file cannot be read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				String line = fileReader.nextLine();
				Student student = readStudent(line); 
				boolean duplicateStudent = false;

				for (int i = 0; i < students.size(); i++) {
					User s = students.get(i);
					if (student.getId().equals(s.getId())) {
						duplicateStudent = true;
					}
				}
				if (!duplicateStudent) {
					students.add(student);
 
				}
			} catch (IllegalArgumentException e) {
				// Skip the Line
			}
		}
		fileReader.close();
		return students;

	}

	/**
	 * Reads a student record from a single line from the readStudentRecords
	 * file and creates a new Student object from this information
	 * 
	 * @param nextLine
	 *            line from file to read Student information from
	 * @return Student object based on record information
	 */
	private static Student readStudent(String nextLine) throws IllegalArgumentException {
		Scanner lineReader = new Scanner(nextLine);
		lineReader.useDelimiter(",");
		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String password = null;
		int maxCredits = MAX_CREDITS;
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
				maxCredits = lineReader.nextInt();
			}
			lineReader.close();
			return new Student(firstName, lastName, id, email, password, maxCredits);

		} catch (IllegalArgumentException e) {
			lineReader.close();
			throw new IllegalArgumentException();

		}

	}

	/**
	 * Writes the student records from the directory to a new file
	 * 
	 * @param fileName
	 *            new file to write Student records to
	 * @param studentDirectory
	 *            directory from which to pull information to write
	 * @throws IOException
	 *             if file cannot be written
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();
	}

}
