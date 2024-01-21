package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class created to check for conflicts among activities
 * 
 * @author Dinesh Karnati
 */
public interface Conflict {

	/**
	 * Checks for conflict within a given activities
	 * 
	 * @param possibleConflictingActivity The activity to check for conflicts with
	 * @throws ConflictException If a conflict is found, a ConflictException is
	 *                           thrown
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
