/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Deals with the creation of Event objects and the different applications and
 * uses of them such as setting meeting information, setting an events details,
 * etc.
 * 
 * @author Dinesh Karnati
 */
public class Event extends Activity {

	/** The event details for an Event */
	private String eventDetails;

	/**
	 * Constructs an Event with a title, meeting days, time slot, and details
	 * 
	 * @param title        title of the event
	 * @param meetingDays  meeting days of the event
	 * @param startTime    start time of the event
	 * @param endTime      end time of the event
	 * @param eventDetails additional details for the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * Gets the details of the event
	 * 
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets Meeting Days and Times based on given inputs
	 * 
	 * @throws IllegalArgumentException is the given meeting days and times are
	 *                                  invalid i.e if the meeting days are null or
	 *                                  if there are multiple instances of the same
	 *                                  day
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {

		if (meetingDays == null) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		int mondayCount = 0;
		int tuesdayCount = 0;
		int wednesdayCount = 0;
		int thursdayCount = 0;
		int fridayCount = 0;
		int saturdayCount = 0;
		int sundayCount = 0;

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
			} else if (meetingDays.substring(i, i + 1).equals("S")) {
				saturdayCount++;
			} else if (meetingDays.substring(i, i + 1).equals("U")) {
				sundayCount++;
			} else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}

		if (mondayCount > 1 || tuesdayCount > 1 || wednesdayCount > 1 || thursdayCount > 1 || fridayCount > 1
				|| saturdayCount > 1 || sundayCount > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Sets an event's details
	 * 
	 * @param eventDetails the eventDetails to set
	 * @throws IllegalArgumentException if the given event details are invalid i.e if the event details are null
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}

	/**
	 * Gets the short display array of event information
	 * 
	 * @return shortDis String array that contains a title and meeting information
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDis = { "", "", super.getTitle(), super.getMeetingString() };
		return shortDis;
	}

	/**
	 * Gets the long display array of event information
	 * 
	 * @return longDis String array that contains a title, meeting information, and
	 *         event details
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDis = { "", "", super.getTitle(), "", "", super.getMeetingString(), eventDetails};
		return longDis;
	}

	/**
	 * Returns the event objects properties such as title, meeting information, and
	 * event details as a string
	 * 
	 * @return the event object as a string
	 */
	@Override
	public String toString() {
		return super.getTitle() + "," + super.getMeetingDays() + "," + super.getStartTime() + "," + super.getEndTime()
				+ "," + eventDetails;
	}

	/**
	 * Checks to see if the given activity object is a duplicate of the object that
	 * called the method
	 * 
	 * @return whether or not two objects are duplicates
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Event) {
			Event e1 = (Event) activity;
			if (e1.getTitle().equals(this.getTitle())) {
				return true;
			}

		}
		return false;
	}

}
