package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * The CourseRoll sets the enrollment cap for a course between the
 * MIN_ENROLLMENT and MAX_ENROLLMENT The CourseRoll can check if a student can
 * be enrolled in a course based on the number of seats left and whether the
 * student is already enrolled in the class
 * 
 * @author Samantha Ryan
 *
 */
public class CourseRoll {
	private LinkedAbstractList<Student> roll;
	private int enrollmentCap;
	private LinkedQueue<Student> waitlist;
	private static final int WAITLIST_SIZE = 10; 

	/** Minimum enrollment in course */
	private static final int MIN_ENROLLMENT = 10;
	/** Maximum enrollment in course */
	private static final int MAX_ENROLLMENT = 250;
	private Course thisCourse;

	/**
	 * Constructs a CourseRoll object with an enrollment cap If the enrollment
	 * cap is outside the range of Max/ Min enrollment numbers, then an
	 * IllegalArgumentException is thrown
	 * 
	 * @param c
	 *            Course being referenced by this CourseRoll
	 * @param enrollmentCap
	 *            maximum number of students allowed to enroll in this class
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if (c == null || enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		this.enrollmentCap = enrollmentCap;
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		waitlist = new LinkedQueue<Student>(WAITLIST_SIZE);
		thisCourse = c;
	}

	/**
	 * Returns the enrollment cap for this course
	 * 
	 * @return enrollment cap for this course roll
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;

	}

	/**
	 * Sets the enrollment cap to a value If the value is outside the range of
	 * the max/min enrollment numbers, then an IllegalArgumentException is
	 * thrown
	 * 
	 * @param newCap
	 *            new enrollment capacity for the course roll
	 */
	public void setEnrollmentCap(int newCap) {
		if (newCap < MIN_ENROLLMENT || newCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		if (roll != null && newCap < roll.size()) {

			throw new IllegalArgumentException();

		}

		if (roll != null) {
			roll.setCapacity(newCap);
			this.enrollmentCap = newCap;
		}
	}

	/**
	 * Adds a student to the roll of the class if the student isn't alreayd in
	 * the class and if the class isn't already full
	 * 
	 * @param s
	 *            student to enroll in the course roll
	 */
	public void enroll(Student s) {

		if (roll == null || s == null) {
			throw new IllegalArgumentException();
		}

		// Check that student s isn't already in the list
		if (roll.contains(s)) {
			throw new IllegalArgumentException();
		}

		if (roll.size() == enrollmentCap) {

			if (waitlist.size() < WAITLIST_SIZE) {
				waitlist.enqueue(s);
			} else {
				throw new IllegalArgumentException();
			}
		} else {

			roll.add(roll.size(), s);

		}

	}

	/**
	 * Drops a student from the course
	 * 
	 * @param s
	 *            student to remove from the course
	 */
	public void drop(Student s) {

		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (roll == null || roll.size() == 0) {
			throw new IllegalArgumentException();
		}
		if (roll.contains(s)) {
			try {
				roll.remove(s);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}

			if (!waitlist.isEmpty()) {
				Student fromWaitList = waitlist.dequeue();
				roll.add(roll.size(), fromWaitList);
				fromWaitList.getSchedule().addCourseToSchedule(thisCourse);
			}
		} else {
			for (int i = 0; i < waitlist.size(); i++) {
				Student inLine = waitlist.dequeue();
				if (!s.equals(inLine)) {
					waitlist.enqueue(inLine);
				}
			}
		}
	}

	/**
	 * Returns the number of seats still available in the course If the returned
	 * value is 0, then the course is full
	 * 
	 * @return number of seats still open in the class
	 */
	public int getOpenSeats() {
		return (this.getEnrollmentCap() - roll.size());
	}

	/**
	 * Returns the number of students currently on the course's waitlist
	 * 
	 * @return number of students currently on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

	/**
	 * Checks whether a student can be enrolled in this course If the student is
	 * already enrolled or if the course is full, then the student cannot be
	 * enrolled
	 * 
	 * @param s
	 *            student looking to be enrolled
	 * @return false if the course is full or if the student is already
	 *         enrolled; true otherwise
	 */
	public boolean canEnroll(Student s) {
		if (getOpenSeats() == 0 || roll.contains(s)) {
			return false;
		}

		return true;
	}
}