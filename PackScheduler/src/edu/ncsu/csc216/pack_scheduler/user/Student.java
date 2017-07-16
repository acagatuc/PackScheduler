package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Contains state and behavior for Student object based on a Studnet's first
 * name, last name, email address, ID, password, and max credits allowed Student
 * object to be used in the PackScheuler program
 * 
 * @author Samantha Ryan
 *
 */
public class Student extends User implements Comparable<Student> {
	private Schedule studentSchedule;
	/** Max credits student is allowed to have */
	private int maxCredits;

	/** Default Max Credits */
	public static final int MAX_CREDITS = 18;

	/** Default Min Credits */
	public static final int MIN_CREDITS = 3;

	/**
	 * Constructs a new Student object based on all fields using setter methods
	 * 
	 * @param firstName
	 *            of Student
	 * @param lastName
	 *            of Student
	 * @param id
	 *            of Student
	 * @param email
	 *            of Student
	 * @param hashPW
	 *            of Student
	 * @param maxCredits
	 *            Student is allowed to take
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		studentSchedule = new Schedule();
		setMaxCredits(maxCredits);
	}

	/**
	 * Constructs a new Student object based on all fields except for max
	 * credits using setter methods.
	 * 
	 * @param firstName
	 *            of Student
	 * @param lastName
	 *            of Student
	 * @param id
	 *            of Student
	 * @param email
	 *            of Student
	 * @param hashPW
	 *            of Student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Returns the max credits a Student may take
	 * 
	 * @return maxCredits of Student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the Student's max credit load to an int parameter.
	 * 
	 * @param maxCredits
	 *            the maxCredits to set
	 * @throws IllegalArgumentException
	 *             if max credits is less than 3 or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < MIN_CREDITS || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Gets the stucent's schedule
	 * 
	 * @return studentSchedule of the Student
	 */
	public Schedule getSchedule() {
		return studentSchedule;
	}

	/**
	 * Returns whether a particular course can be added to this student's
	 * schedule based on whether the course creates a conflict, is a duplicate,
	 * is null, or would cause the total credit hours to be greater than the
	 * maximum allowed
	 * 
	 * @param c
	 *            course to test
	 * @return true if the course can be added to this schedule; false if
	 *         otherwise
	 */
	public boolean canAdd(Course c) {
		if (!this.getSchedule().canAdd(c)) {
			return false; 
		}
		int enrolledCredits = this.getSchedule().getScheduleCredits();
		if (enrolledCredits + c.getCredits() > maxCredits) {
			return false;
		}

		return true;
	}

	/**
	 * Generates a String representation of the Student based on all fields
	 * 
	 * @return String representation of Student
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCredits;
	}

	/**
	 * Compares this Student to another Student based on last name, first name,
	 * id
	 * 
	 * @return 1 if this Student is greater than another student, 0 if they are
	 *         equal, and -1 if this student is less than another student
	 */
	@Override
	public int compareTo(Student s) {
		if (s == null) {
			throw new NullPointerException();
		}

		if (this.getClass() != s.getClass()) {
			throw new ClassCastException();
		}

		int lastCompare = this.getLastName().compareTo(s.getLastName());
		int firstCompare = this.getFirstName().compareTo(s.getFirstName());
		int idCompare = this.getId().compareTo(s.getId());

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
	 * Generates a hashCode representation for the Student
	 * 
	 * @return hashCode for Student
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Returns true if this Student object is equal another Student object
	 * 
	 * @return true if the two Students are equal on all fields
	 */
	@Override
	public boolean equals(Object obj) { 
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}
}
