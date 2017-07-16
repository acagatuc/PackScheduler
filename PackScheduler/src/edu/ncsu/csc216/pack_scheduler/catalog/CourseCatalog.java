/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.*;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Maintains a directory of all courses available at NCSU Each course has a
 * unique Name and Section
 * 
 * @author Samantha Ryan
 *
 */
public class CourseCatalog {

	/** List of courses in the directory */
	private SortedList<Course> courseCatalog;

	/**
	 * Creates an empty student directory
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Creates an empty course catalog.
	 */
	public void newCourseCatalog() {
		courseCatalog = new SortedList<Course>();
	}

	/**
	 * Constructs the course catalog by reading in course information from the
	 * given file
	 * 
	 * @throws IllegalArgumentException
	 *             if the file cannot be found
	 * @param fileName
	 *            file containing list of courses
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			courseCatalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a course to the catalog. Returns true if the course is added and
	 * false if the course is unable to be added because their name and section
	 * match another course's name and section
	 * 
	 * @param name
	 *            name of Course
	 * @param title
	 *            title of Course
	 * @param section
	 *            section of Course
	 * @param credits
	 *            credit hours for Course
	 * @param instructorId
	 *            instructor's unity id
	 * @param meetingDays
	 *            meeting days for Course as series of chars
	 * @param startTime
	 *            start time for Course
	 * @param endTime
	 *            end time for Course
	 * @param enrollmentCap
	 *            the maximum number of students allowed to be enrolled in the
	 *            course
	 * @return true if course is able to be added; false if not
	 * 
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime,
				endTime);

		for (int i = 0; i < courseCatalog.size(); i++) {
			Course c = courseCatalog.get(i);
			if (c.getName().equals(course.getName()) && c.getSection().equals(course.getSection())) {
				return false;
			} 
		}
		courseCatalog.add(course);
		return true;
	}

	/**
	 * Adds an arranged course to the catalog. Returns true if the course is
	 * added and false if the course is unable to be added because their name
	 * and section match another course's name and section
	 * 
	 * @param name
	 *            name of Course
	 * @param title
	 *            title of Course
	 * @param section
	 *            section of Course
	 * @param credits
	 *            credit hours for Course
	 * @param instructorId
	 *            instructor's unity id
	 * @param meetingDays
	 *            meeting days for Course as series of chars
	 * @param enrollmentCap
	 *            the maximum number of students allowed to be enrolled in a
	 *            Course
	 */
	public void addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays) {
		addCourseToCatalog(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Removes the course with the given name and section from the course
	 * catalog Returns true if the course is removed and false if the course
	 * cannot be removed
	 * 
	 * @param name
	 *            course name
	 * @param section
	 *            course section
	 * @return true if removed
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < courseCatalog.size(); i++) {
			Course c = courseCatalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				courseCatalog.remove(i);
				return true;
			}
		}
		return false; 
	}

	/**
	 * Returns the course from the catalog with this name and section
	 * 
	 * @param name
	 *            name of course
	 * @param section
	 *            section of course
	 * @return course from catalog; null if course does not exist in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < courseCatalog.size(); i++) {
			Course c = courseCatalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Returns all courses in the catalog with a column for name, section,
	 * title, and meeting information
	 * 
	 * @return String Array containing courses' name, section, title, and
	 *         meeting information
	 */
	public String[][] getCourseCatalog() {
		String[][] catalog = new String[courseCatalog.size()][5];
		for (int i = 0; i < courseCatalog.size(); i++) {
			Course c = courseCatalog.get(i);
			catalog[i][0] = c.getName();
			catalog[i][1] = c.getSection();
			catalog[i][2] = c.getTitle();
			catalog[i][3] = c.getMeetingString();
			catalog[i][4] = "" + c.getCourseRoll().getOpenSeats();
		} 
		return catalog;
	}

	/**
	 * Saves all courses in the catalog to a file
	 * 
	 * @param fileName
	 *            name of file to save courses to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, courseCatalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

}