package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Course class to create a Course object that has the fields name, title,
 * section, credits, instructor ID, meeting days, start time, and end time
 * 
 * @author Dinesh Karnati
 */
public class Course extends Activity {

	/** Minimum length for a course name */
	private static final int MIN_NAME_LENGTH = 5;
	/** Maximum length for a course name */
	private static final int MAX_NAME_LENGTH = 8;
	/** Maximum credits for a course */
	private static final int MAX_CREDITS = 5;
	/** Minimum credits for a course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Gets the Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the given name is invalid i.e the name is
	 *                                  null or less than the minimum or greater
	 *                                  than the max name length or if the given
	 *                                  format of the name is incorrect (no. of
	 *                                  letters and digits)
	 */
	private void setName(String name) {
		// Throw an exception if the name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Throw exception if name is an empty string
		// Throw exception if the name contains less than 5 characters or greater than 8
		// characters
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Check for pattern of L[LLL] NNN

		int letterCounter = 0;
		int digitsCounter = 0;
		boolean hasSpace = false;
		String letters = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";

		for (int i = 0; i < name.length(); i++) {
			if (!hasSpace) {
				if (letters.indexOf(name.substring(i, i + 1).toLowerCase()) >= 0) {
					letterCounter++;
				} else if (name.substring(i, i + 1).equals(" ")) {
					hasSpace = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (hasSpace) {
				if (digits.indexOf(name.substring(i, i + 1).toLowerCase()) >= 0) {
					digitsCounter++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}

		if (letterCounter < 1 || letterCounter > 4) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		if (digitsCounter != 3) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * Gets the course's Section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's Section
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the given section is invalid i.e if the
	 *                                  section number is null or less than 3 or if
	 *                                  it doesn't contain all digits
	 */
	public void setSection(String section) {
		if (section == null || section.length() != 3) {
			throw new IllegalArgumentException("Invalid section.");
		}

		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Gets the Course's Credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's Section
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the given credits are invalid i.e if the
	 *                                  credits are lower than the min or greater
	 *                                  than the max a student can have
	 */
	public void setCredits(int credits) {

		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * Gets the Course's Instructor ID
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets Courses's Instructor ID
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if the given instructor id is invalid i.e if
	 *                                  the Id is null or empty
	 */
	public void setInstructorId(String instructorId) {

		if (instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns the integer hash value of an input
	 * 
	 * @return result hashed value of input
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

	/**
	 * Checks to see if two objects are equal
	 * 
	 * @return whether or not the two objects are equal (true or false)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
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

	/*
	 * Returns the Course object as a string i.e with all its fields Returns a comma
	 * separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays()
				+ "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Gets the short display array of information
	 * 
	 * @return shortDisplay String array with course's name, section, title and
	 *         meeting information
	 */
	public String[] getShortDisplayArray() {

		String[] shortDisplay = { name, section, getTitle(), getMeetingString() };
		return shortDisplay;
	}

	/**
	 * Gets the long display array of information
	 * 
	 * @return longDisplay String array with course's name, section, title, credits,
	 *         instructor id and meeting information
	 */
	public String[] getLongDisplayArray() {

		String[] longDisplay = { name, section, getTitle(), String.valueOf(credits), instructorId, getMeetingString(),
				"" };
		return longDisplay;
	}

	/**
	 * Sets the meeting days and times based on given inputs
	 * 
	 * @throws IllegalArgumentException if the given meeting days and times are
	 *                                  invalid i.e if the course if arranged but
	 *                                  has start and end times or if the course is
	 *                                  not arranged but has class on multiple of
	 *                                  the same days
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {

		if ("A".equals(meetingDays) && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");

		}

		int arrangedCount = 0;
		int mondayCount = 0;
		int tuesdayCount = 0;
		int wednesdayCount = 0;
		int thursdayCount = 0;
		int fridayCount = 0;

		if (meetingDays.contains("A") && meetingDays.length() > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else {
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.substring(i, i + 1).equals("M")) {
					mondayCount++;
				} else if (meetingDays.substring(i, i + 1).equals("T")) {
					tuesdayCount++;
				} else if (meetingDays.substring(i, i + 1).equals("W")) {
					wednesdayCount++;
				} else if (meetingDays.substring(i, i + 1).equals("H")) {
					thursdayCount++;
				} else if (meetingDays.substring(i, i + 1).equals("F")) {
					fridayCount++;
				} else if (meetingDays.substring(i, i + 1).equals("A")) {
					arrangedCount++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

		}
		if (mondayCount > 1 || tuesdayCount > 1 || wednesdayCount > 1 || thursdayCount > 1 || fridayCount > 1
				|| arrangedCount > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);

	}

	/**
	 * Checks to see if the given activity object is a duplicate of the object that
	 * called the method
	 * 
	 * @return whether or not two objects are duplicates
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course c1 = (Course) activity;
			if (c1.getName().equals(this.getName())) {
				return true;
			}

		}
		return false;
	}
}
