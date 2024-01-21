package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Deals with the creation of Activity objects and the different applications
 * and uses of them
 * 
 * @author Dinesh Karnati
 */
public abstract class Activity implements Conflict {

	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Checks whether there are any conflicts between two given activities Conflicts
	 * arise when two activities run on at least one same day and have overlap with
	 * class times
	 * 
	 * @param possibleConflictingActivity activity that could have a conflict with
	 *                                    the other given activity
	 * @throws ConflictException if there is a conflict
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {

		if ("A".equals(possibleConflictingActivity.getMeetingDays()) && "A".equals(this.getMeetingDays())) {
			return;
		}

		for (int i = 0; i < this.getMeetingDays().length(); i++) {
			if (possibleConflictingActivity.getMeetingDays().indexOf(this.getMeetingDays().substring(i, i + 1)) != -1
					&& this.getStartTime() <= possibleConflictingActivity.getEndTime()
					&& this.getEndTime() >= possibleConflictingActivity.getStartTime()) {
				throw new ConflictException("Schedule conflict.");
			}
		}
	}

	/**
	 * Builds an activity with given title, meeting days, start time, and end time
	 * 
	 * @param title       title of the activity
	 * @param meetingDays meeting days of the activity
	 * @param startTime   start time of the activity
	 * @param endTime     end time of the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Gets the Activity's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's Title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the given title is invalid i.e if the
	 *                                  title is null or is empty
	 * 
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Gets Activity's Meeting Days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Gets Activity's Start Time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Gets Activity's End Time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets Activity's Meeting Days, Start Time, and End Time
	 * 
	 * @param meetingDays the meeting days to set
	 * @param startTime   the start time to set
	 * @param endTime     the end time to set
	 * @throws IllegalArgumentException if the given meeting days and times are
	 *                                  invalid i.e if the meeting days are null or
	 *                                  empty or if the time given does not exist on
	 *                                  the 24 hour clock or if the start time is
	 *                                  after the end time
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;

		if (startHour < 0 || startHour > 23) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (startMin < 0 || startMin > 59) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endHour < 0 || endHour > 23) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endMin < 0 || endMin > 59) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Converts military time to standard time and returns meeting days as well as
	 * time slot for course
	 * 
	 * @return meeting days and time slot string
	 */
	String getTimeString() {

		int startHours = this.startTime / 100;
		int endHours = this.endTime / 100;
		boolean isStartPM = false;
		boolean isEndPM = false;
		String startMinutes = String.valueOf(this.startTime % 100).toString();
		String endMinutes = String.valueOf(this.endTime % 100).toString();

		if (startHours >= 12) {
			isStartPM = true;
		}

		if (endHours >= 12) {
			isEndPM = true;
		}

		if (startHours > 12) {
			startHours = startHours - 12;
		}

		if (endHours > 12) {
			endHours = endHours - 12;
		}

		if (startHours == 0) {
			startHours = 12;
		}

		if (endHours == 0) {
			endHours = 12;
		}

		if (Integer.valueOf(startMinutes) < 10) {
			startMinutes = "0" + startMinutes;
		}

		if (Integer.valueOf(endMinutes) < 10) {
			endMinutes = "0" + endMinutes;
		}

		if (isStartPM && isEndPM) {
			return this.meetingDays + " " + startHours + ":" + startMinutes + "PM-" + endHours + ":" + endMinutes
					+ "PM";
		} else if (!isStartPM && !isEndPM) {
			return this.meetingDays + " " + startHours + ":" + startMinutes + "AM-" + endHours + ":" + endMinutes
					+ "AM";
		} else {
			return this.meetingDays + " " + startHours + ":" + startMinutes + "AM-" + endHours + ":" + endMinutes
					+ "PM";
		}

	}

	/**
	 * Gets meeting days and time slot
	 * 
	 * @return meeting days and time slot string
	 */
	public String getMeetingString() {

		if ("A".equals(this.meetingDays)) {
			return "Arranged";
		} else {
			return this.getTimeString();
		}
	}

	/**
	 * Returns the integer hash value of an input
	 * 
	 * @return result hashed value of input
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
	 * Checks to see if two objects are equal
	 * 
	 * @return whether or not the objects are equal (true or false)
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
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Gets the short display array of information
	 * 
	 * @return a string array with the necessary course information
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Gets the long display array of information
	 * 
	 * @return a string array with the necessary final course schedule information
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Used to check whether the two objects trying to be compared are duplicates
	 * 
	 * @param activity activity object trying to be compared to the object calling
	 *                 the method
	 * @return whether two objects trying to be compared are the same
	 */
	public abstract boolean isDuplicate(Activity activity);

}