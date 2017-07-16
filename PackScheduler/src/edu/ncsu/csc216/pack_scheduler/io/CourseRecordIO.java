package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import edu.ncsu.csc216.collections.list.SortedList;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Samantha Ryan
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses.
	 * Any invalid Courses are ignored. If the file to read cannot be found or
	 * the permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName
	 *            file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException
	 *             if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		while (fileReader.hasNextLine()) {
			try {
				String line = fileReader.nextLine();
				Course course = readCourse(line);
				boolean duplicate = false;

				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (c.getName().equals(course.getName()) && c.getSection().equals(course.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}

			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Reads Course information from the passed string
	 * 
	 * @param nextLine
	 *            string to read information from
	 * @return Course based on passed string
	 */
	private static Course readCourse(String nextLine) throws IllegalArgumentException {
		Scanner lineReader = new Scanner(nextLine);
		lineReader.useDelimiter(",");

		try {
			String name = lineReader.next();
			String title = lineReader.next();
			String section = lineReader.next();
			int credits = lineReader.nextInt();
			String newId = null;
			if (!lineReader.hasNextInt()) {
				newId = lineReader.next();
			}
			int enrollmentCap = lineReader.nextInt();
			String meetingDays = lineReader.next();

			if (meetingDays.equals("A") && lineReader.hasNext()) {
				lineReader.close();
				throw new IllegalArgumentException();
			}

			// Regular meeting days with missing time//
			if (!meetingDays.equals("A") && !lineReader.hasNextInt()) {
				lineReader.close();
				throw new NoSuchElementException();
			}

			if (!meetingDays.equals("A") && lineReader.hasNextInt()) {
				int startTime = lineReader.nextInt();
				if (startTime == 0) {
					lineReader.close();
					throw new IllegalArgumentException();
				}
				// Check if there's an end time//
				if (!lineReader.hasNextInt()) {
					lineReader.close();
					throw new NoSuchElementException();
				} else {
					int endTime = lineReader.nextInt();
					lineReader.close();
					Course nc = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime,
							endTime);

					Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(newId);
					if (f != null) {
						f.getSchedule().addCourseToSchedule(nc);
					}
					return nc; 
				}
			}
			lineReader.close();
			Course nc = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);

			Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(newId);
			if (f != null) {
				f.getSchedule().addCourseToSchedule(nc);
			}

			return nc;

		} catch (

		NoSuchElementException e) {
			lineReader.close();
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Writes the course record information to a file
	 * 
	 * @param fileName
	 *            file name to write course information to
	 * @param courses
	 *            courses to write to the file
	 * @throws IOException
	 *             if file cannot be written to
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}