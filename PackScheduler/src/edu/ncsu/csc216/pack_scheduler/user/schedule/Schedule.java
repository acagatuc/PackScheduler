/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Contains information about the Schedule object
 * 
 * @author Samantha Ryan
 *
 */
public class Schedule {
	private ArrayList<Course> schedule;
	private String title;

	/**
	 * Constructs a Schedule object with a default empty ArrayList and title "My
	 * Schedule" 
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Adds a particular course to the schedule if there is no conflict and if
	 * it is not a duplicate course (duplicate meaning same course name,
	 * regardless of section
	 * 
	 * @param c
	 *            course to be added to the schedule
	 * @return true if course is able to be added to schedule; false if
	 *         otherwise
	 * @throws IllegalArgumentException
	 *             if there is a conflict in the scheduling
	 */
	public boolean addCourseToSchedule(Course c) throws IllegalArgumentException {
		if (c == null) {
			throw new NullPointerException();
		}
		// Check for duplicate course and conflict
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getName().equals(c.getName())){
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			} else {
				try {
					c.checkConflict(schedule.get(i));
				} catch (ConflictException e) {
					throw new IllegalArgumentException("The course cannot be added due to a conflict.");
				}
			}
		}

		// If you've made it this far, go ahead and add the course!
		schedule.add(schedule.size(), c);

		// TO DO
		return true;
	}

	/**
	 * Removes a course from the schedule if it is in the schedule
	 * 
	 * @param c
	 *            course to be removed from the schedule
	 * @return true if the course is able to be removed from the schedule; false
	 *         otherwise
	 */
	public boolean removeCourseFromSchedule(Course c) {
		if (schedule.contains(c)) {
			schedule.remove(c);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Resets the schedule to the default settings (removes all courses)
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Returns a 2D String array representation of the courses in the schedule
	 * 
	 * @return Strin[][] array representation of the schedule's courses
	 */
	public String[][] getScheduledCourses() {

		String[][] s = new String[schedule.size()][];
		for (int i = 0; i < s.length; i++) {
			s[i] = schedule.get(i).getShortDisplayArray();
		}
		return s;
	}

	/**
	 * Sets the title to a passed String
	 * 
	 * @param t
	 *            title of schedule
	 */
	public void setTitle(String t) {
		if (t == null) {
			throw new IllegalArgumentException("Title cannot be null");
		}
		this.title = t;
	}

	/**
	 * Gets the title of the schedule
	 * 
	 * @return schedule title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the size FOR TESTING
	 * 
	 * @return size / number of classes in the schedule
	 */
	protected int getSize() {
		return schedule.size();
	}

	/**
	 * Gets the number of credits that the student is currently enrolled in
	 * 
	 * @return number of credits the student is currently enrolled in
	 */ 
	public int getScheduleCredits() {

		if (schedule.size() == 0) {
			return 0;
		}

		int scheduledCredits = 0;
		for (int i = 0; i < getSize(); i++) {
			scheduledCredits += schedule.get(i).getCredits();
		}
		return scheduledCredits;
	}

	/**
	 * Returns whether a particular course can be added to the schedule based on
	 * whether the course creates a conflict, is a duplicate, is null, or would
	 * cause the total credit hours to be greater than the maximum allowed
	 * 
	 * @param c course to test
	 * @return true if the course can be added to this schedule; false if otherwise
	 */
	public boolean canAdd(Course c) {
		if (c == null) { 
			return false; 
		}

		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getName().equals(c.getName())){
				return false;
			}
			try {
				schedule.get(i).checkConflict(c);
			} catch (ConflictException e) {
				return false;
			}
		}
 
		return true;
	}
}
