/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;

/**
 * Course object to be used in the WolfScheduler GP1 project Contains a Course's
 * name, title, section, credit hours, instructorId, and meeting time
 * information to be used in the WolfSchedulerGUI project
 * 
 * Courses can be added to a student's Schedule and to the Course Catalog in the
 * WolfScheduler class
 * 
 * @author Samantha Ryan
 *
 */
public class Course extends Activity implements Comparable<Course> {
	/** length of Course's name */
	public static final int SECTION_LENGTH = 3;
	/** Minimum length of Course's name */
	public static final int MIN_NAME_LENGTH = 4;
	/** Maximum length of Course's name */
	public static final int MAX_NAME_LENGTH = 6;
	/** Max Course credits */
	public static final int MAX_CREDITS = 5;
	/** Min Course credits */
	public static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's Roll */
	private CourseRoll roll;

	/**
	 * Creates a Course object with values for all fields
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
	 * @param enrollmentCap
	 *            TODO
	 * @param meetingDays
	 *            meeting days for Course as series of chars
	 * @param startTime
	 *            start time for Course
	 * @param endTime
	 *            end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);

		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);

		roll = new CourseRoll(this, enrollmentCap);

	}

	/**
	 * Creates a Course with the given name, title, section, credits,
	 * instructorId, and meetingDays for courses that are arranged.
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
	 * 
	 * @param enrollmentCap
	 *            the maximum number of students allowed to be enrolled in the
	 *            Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	} 

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * 
	 * @param name
	 *            the name to set
	 * @throws IllegalArgumentException
	 *             if name is null or length is less than 4 or greater than 6
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid name");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid name");
		}
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section. If the section is not exactly 3 digits, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param section
	 *            the section to set
	 * @throws IllegalArgumentException
	 *             if section is null or empty String.
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section number");
		}
		for (int i = 0; i < SECTION_LENGTH; i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section number");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credit hours.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credit hours. If credit hours are not a number or if
	 * they are less than 1 or greater than 5, an IllegalArgumentException is
	 * thrown
	 * 
	 * @param credits
	 *            the credits to set
	 * @throws IllegalArgumentException
	 *             if credits are greater than 5 or less than 1
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credit hours");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructor ID.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Returns the roll for this course 
	 * @return the roll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Sets the Course's instructor ID. If the Instructor's ID is null or an
	 * empty set, an IllegalArgumentException is thrown
	 * 
	 * @param instructorId
	 *            the instructorId to set
	 * 
	 */
	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	/**
	 * sets the meetingDays for the course
	 * 
	 * @param meetingDays
	 *            meeting days for the course
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		for (int i = 0; i < meetingDays.length(); i++) {
			if (!(meetingDays.charAt(i) == 'A' || meetingDays.charAt(i) == 'M' || meetingDays.charAt(i) == 'T'
					|| meetingDays.charAt(i) == 'W' || meetingDays.charAt(i) == 'H' || meetingDays.charAt(i) == 'F')) {
				throw new IllegalArgumentException("Invalid meeting days");
			}
		}
		if (meetingDays.contains("A") && meetingDays.length() > 1) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		super.setMeetingDays(meetingDays);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	@Override
	public String[] getShortDisplayArray() {
		String openSeats = "";
		openSeats += roll.getOpenSeats();
		String[] shortDisplay = { name, section, getTitle(), getMeetingString(), openSeats };
		return shortDisplay;
	}
 
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = { name, section, getTitle(), "" + credits, instructorId, getMeetingString(), "" };
		return longDisplay;
	}

	/**
	 * Checks if an activity object is a Course and whether that course is a
	 * duplicate
	 * 
	 * @param activity
	 *            Activity to be compared
	 * @return true if activity is a duplicate course
	 */
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course other = (Course) activity;
			return this.getName().equals(other.getName());
		}
		return false;
	}

	/**
	 * Compares two courses to determine which falls first and second in an
	 * ordered list (or if they are the same value)
	 * 
	 * @return -1 if this course appears before the second course, 0 if they are
	 *         the same, and 1 if this course appears after the second course
	 */
	@Override
	public int compareTo(Course c) {
		if (c == null) {
			throw new NullPointerException();
		}

		if (this.getClass() != c.getClass()) {
			throw new ClassCastException();
		}

		int nameCompare = this.getName().compareTo(c.getName());
		int sectionCompare = this.getSection().compareTo(c.getSection());

		if (nameCompare < 0) {
			return -1;
		} else if (nameCompare > 0) {
			return 1; 
		} else if (sectionCompare < 0) {
			return -1;
		} else if (sectionCompare > 0) {
			return 1;
		} else {
			return 0;
		}
	}

}