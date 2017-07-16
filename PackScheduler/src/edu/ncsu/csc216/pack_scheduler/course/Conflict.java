
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Defines conflict behavior between Activities
 * 
 * @author Samantha Ryan
 *
 */
public interface Conflict {

	/**
	 * Checks that an Activity is not in conflict with another Activity in the
	 * schedule
	 * 
	 * @param possibleConflictingActivity
	 *            Activity to be checked against others in schedule
	 * @throws ConflictException
	 *             if the new Activity conflicts with others in the schedule
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
