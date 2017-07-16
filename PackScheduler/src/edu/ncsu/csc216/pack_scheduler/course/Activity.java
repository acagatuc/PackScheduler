package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Abstract superclass Activity representing different
 * 
 * @author Samantha Ryan
 *
 */
public abstract class Activity implements Conflict {

	/**
	 * Implements checkConflict()
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		boolean sameTime = false;
		boolean sameDay = false;

		if ((this.getStartTime() <= possibleConflictingActivity.getStartTime()
				&& this.getEndTime() >= possibleConflictingActivity.getStartTime())
				|| (this.getStartTime() <= possibleConflictingActivity.getEndTime()
						&& this.getEndTime() >= possibleConflictingActivity.getEndTime())
				|| (this.getStartTime() >= possibleConflictingActivity.getStartTime()
						&& this.getEndTime() <= possibleConflictingActivity.getEndTime())
				|| (this.getStartTime() <= possibleConflictingActivity.getStartTime()
						&& this.getEndTime() >= possibleConflictingActivity.getEndTime())) {
			sameTime = true;
		}
 
		int search = -1;
		for (int i = 0; i < this.getMeetingDays().length(); i++) {
			char x = this.getMeetingDays().charAt(i);
			search = possibleConflictingActivity.getMeetingDays().indexOf(x);
			if (search > -1) {
				sameDay = true;
			}
		}
		
		if(this.getMeetingDays().equals("A") || possibleConflictingActivity.getMeetingDays().equals("A")){
			sameDay = false;
		}

		if (sameDay && sameTime) {
			throw new ConflictException();
		}

	}

	/** The upper military time */
	public static final int UPPER_TIME = 2400;
	/** The upper value of an hour in minutes */
	public static final int UPPER_HOUR = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructs a new Activity based on an event's title, meeting days, and
	 * start and end time.
	 * 
	 * @param title
	 *            Title of activity
	 * @param meetingDays
	 *            Meeting days of activities
	 * @param startTime
	 *            Start time of activity
	 * @param endTime
	 *            End time of activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Returns an array representing a shortened Activity record
	 * 
	 * @return short String array of activity record
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns an array representing an extended Activity record
	 * 
	 * @return long String array of activity record
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Returns the Course's title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title. If the title is null or has a length of 0, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param title
	 *            the title to set
	 * @throws IllegalArgumentException
	 *             if title is null or Empty string.
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("Invalid title");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's meeting days.
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Course's meeting days. If a meeting day is not M, T, W, H, F, or
	 * A, then an IllegalArgumentException is thrown. If the meeting days are
	 * arranged ("A"), then no other meeting day character can be in the
	 * meetingDay string.
	 * 
	 * @param meetingDays
	 *            the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {

		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the Course's start and end times
	 * 
	 * @param startTime
	 *            start time of Course
	 * @param endTime
	 *            end time of Course
	 */
	public void setActivityTime(int startTime, int endTime) {
		if ((startTime > endTime)
				|| (startTime < 0 || startTime > UPPER_TIME - 1 || endTime < 0 || endTime > UPPER_TIME - 1)
				|| (startTime % 100 >= UPPER_HOUR - 1 || endTime % 100 > UPPER_HOUR - 1)) {
			throw new IllegalArgumentException("Invalid course times");
		}

		if (!getMeetingDays().equals("A") && (startTime == 0 || endTime == 0)) {
			throw new IllegalArgumentException("Invalid course times");
		}
		this.startTime = startTime;
		this.endTime = endTime;

		if (getMeetingDays().equals("A")) {
			this.startTime = 0;
			this.endTime = 0;
		}

	}

	/**
	 * Returns a string representing the meeting time / day information of the
	 * Course
	 * 
	 * @return string of meeting time
	 */
	public String getMeetingString() {
		String days = getMeetingDays();
		if (days.equals("A")) {
			return "Arranged";
		}

		int start = getStartTime();
		int end = getEndTime();
		int hours;
		int minutes;
		String sStartTime = "";
		String sEndTime = "";

		boolean isPM = false;

		if (start >= UPPER_TIME / 2) {
			isPM = true;

		}
		hours = start / 100;
		minutes = start % 100;

		if (hours > 12) {
			hours -= 12;
		}

		if (!isPM) {
			if (minutes % 100 == 0) {
				sStartTime = hours + ":" + minutes + "0AM";
			} else {
				sStartTime = hours + ":" + minutes + "AM";
			}
		} else {
			if (minutes % 100 == 0) {
				sStartTime = hours + ":" + minutes + "0PM";
			} else {
				sStartTime = hours + ":" + minutes + "PM";
			}
		}

		isPM = false;
		if (end >= UPPER_TIME / 2) {
			isPM = true;
		}
		hours = end / 100; 
		minutes = end % 100;
		if (hours > 12) {
			hours -= 12;
		}

		if (!isPM) {
			if (minutes % 100 == 0) {
				sEndTime = hours + ":" + minutes + "0AM";
			} else {
				sEndTime = hours + ":" + minutes + "AM";
			}
		} else {
			if (minutes % 100 == 0) {
				sEndTime = hours + ":" + minutes + "0PM";
			} else {
				sEndTime = hours + ":" + minutes + "PM";
			}
		} 

		return getMeetingDays() + " " + sStartTime + "-" + sEndTime;
	}

	/**
	 * Determines if an Activity is a duplicate object
	 * 
	 * @param activity
	 *            Activity to compare
	 * @return true if activity is duplicated object
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns a hash code representation of the Activity
	 * 
	 * @return hash code representation of Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Determines whether an object is equal to this object on all fields
	 * 
	 * @return true if object is equal to this object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime) {
			return false;
		}
		if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime) {
			return false;
		}
		if (!title.equals(other.title))
			return false;
		return true;
	}

}