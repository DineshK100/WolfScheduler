/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Custom exception thrown when a conflict is detected between activities
 * 
 * @author Dinesh Karnati
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a ConflictException with a specified detail message.
     *
     * @param conflictMessage The message describing the conflict.
     */
	public ConflictException(String conflictMessage) {
		super(conflictMessage);
	}
	
    /**
     * Constructs a ConflictException with the default message for schedule conflicts.
     */
	public ConflictException() {
        this("Schedule conflict.");
    }

}
