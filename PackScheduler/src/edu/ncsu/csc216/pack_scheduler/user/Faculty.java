package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/** 
 * Contains state and behavior for Faculty object based on a Faculty's first
 * name, last name, email address, ID, password, and max courses allowed. Object
 * to be used in the PackScheuler program
 * 
 * @author Samantha Ryan
 *
 */
public class Faculty extends User implements Comparable<Faculty> {

	private FacultySchedule facultySchedule;
	/** Max Courses Faculty is allowed to have */
	private int maxCourses;

	/** Default Max Courses */
	public static final int MAX_COURSES = 3;

	/** Default Min Courses */
	public static final int MIN_COURSES = 1;

	/**
	 * Constructs a new Faculty object based on all fields using setter methods
	 * 
	 * @param firstName
	 *            of Faculty
	 * @param lastName
	 *            of Faculty
	 * @param id
	 *            of Faculty
	 * @param email
	 *            of Faculty
	 * @param hashPW
	 *            of Faculty
	 * @param maxCourses
	 *            Faculty is allowed to take
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		facultySchedule = new FacultySchedule(id);
		setMaxCourses(maxCourses);
	}

	/**
	 * Checks whether the faculty member has an overloaded schedule (is
	 * scheduled for more classes than their max course limit)
	 * 
	 * @return true if the faculty's schedule is overloaded
	 */
	public boolean isOverloaded() {
		if (facultySchedule.getScheduledCourses().length > maxCourses) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the max credits a Faculty may take
	 * 
	 * @return maxCourses of Faculty
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the Faculty's max credit load to an int parameter.
	 * 
	 * @param maxCourses
	 *            the maxCourses to set
	 * @throws IllegalArgumentException
	 *             if max credits is less than 3 or greater than 18
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Gets the stucent's schedule
	 * 
	 * @return studentSchedule of the Faculty
	 */
	public FacultySchedule getSchedule() {
		return facultySchedule;
	}

	/**
	 * Generates a String representation of the Faculty based on all fields
	 * 
	 * @return String representation of Faculty
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCourses;
	}

	/**
	 * Compares this Faculty to another Faculty based on last name, first name,
	 * id
	 * 
	 * @return 1 if this Faculty is greater than another student, 0 if they are
	 *         equal, and -1 if this student is less than another student
	 */
	@Override
	public int compareTo(Faculty f) {
		if (f == null) {
			throw new NullPointerException();
		}

		if (this.getClass() != f.getClass()) {
			throw new ClassCastException();
		}

		int lastCompare = this.getLastName().compareTo(f.getLastName());
		int firstCompare = this.getFirstName().compareTo(f.getFirstName());
		int idCompare = this.getId().compareTo(f.getId());

		if (lastCompare < 0) {
			return -1;
		} else if (lastCompare > 0) {
			return 1;
		} else if (firstCompare < 0) {
			return -1;
		} else if (firstCompare > 0) {
			return 1;
		} else if (idCompare < 0) {
			return -1;
		} else if (idCompare > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Generates a hashCode representation for the Faculty
	 * 
	 * @return hashCode for Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Returns true if this Faculty object is equal another Faculty object
	 * 
	 * @return true if the two Facultys are equal on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}
}
