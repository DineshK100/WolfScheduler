/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * Adds functionality to the the scheduler by letting the user add activities,
 * remove activities, etc from their schedule
 * 
 * @author Dinesh Karnati
 */
public class WolfScheduler {

	/** An array list than contains the different activities available */
	private ArrayList<Course> courseCatalog = new ArrayList<>();
	/** An array list that contains the user's specific schedule */
	private ArrayList<Activity> schedule = new ArrayList<>();
	/** The name the user gives to their schedule */
	private String title;

	/**
	 * Constructs a Wolf scheduler object with its course catalog and schedule title
	 * 
	 * @param file file that contains information regarding the Wolf scheduler
	 * @throws IllegalArgumentException if the file is not found
	 */
	public WolfScheduler(String file) {

		this.title = "My Schedule";

		try {
			courseCatalog = CourseRecordIO.readCourseRecords(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Invalid Course.");
		}

	}

	/**
	 * Gets the different courses along with their names, sections, and titles
	 * 
	 * @return a 2-d array that shows the different courses offered along with their
	 *         name, section, and title
	 */

	public String[][] getCourseCatalog() {

		String[][] catalog = new String[this.courseCatalog.size()][4];

		for (int i = 0; i < this.courseCatalog.size(); i++) {
			catalog[i][0] = courseCatalog.get(i).getName();
			catalog[i][1] = courseCatalog.get(i).getSection();
			catalog[i][2] = courseCatalog.get(i).getTitle();
			catalog[i][3] = courseCatalog.get(i).getMeetingString();

		}
		return catalog;
	}

	/**
	 * Gets the user's schedule along with names, sections, and titles
	 * 
	 * @return a 2-d array that shows the user's schedule along with their name,
	 *         section, and title
	 */
	public String[][] getScheduledActivities() {

		String[][] s = new String[this.schedule.size()][4];

		for (int i = 0; i < this.schedule.size(); i++) {
			String[] shortDisplay = schedule.get(i).getShortDisplayArray();
			s[i][0] = shortDisplay[0];
			s[i][1] = shortDisplay[1];
			s[i][2] = shortDisplay[2];
			s[i][3] = shortDisplay[3];
		}

		return s;

	}

	/**
	 * Gets the user's courses (with full information) along with names, sections,
	 * titles, credits, instructor Id's, and meeting information
	 * 
	 * @return a 2-d array the the user's courses along with names, sections,
	 *         titles, credits, instructor Id's, and meeting information
	 */

	public String[][] getFullScheduledActivities() {

		String[][] fullSchedule = new String[this.schedule.size()][7];

		for (int i = 0; i < this.schedule.size(); i++) {
			String[] longDisplay = schedule.get(i).getLongDisplayArray();

			fullSchedule[i][0] = longDisplay[0];
			fullSchedule[i][1] = longDisplay[1];
			fullSchedule[i][2] = longDisplay[2];
			fullSchedule[i][3] = longDisplay[3];
			fullSchedule[i][4] = longDisplay[4];
			fullSchedule[i][5] = longDisplay[5];
			fullSchedule[i][6] = longDisplay[6];
		}

		return fullSchedule;
	}

	/**
	 * Saves the student's schedule
	 * 
	 * @param filename name of the file with
	 * @throws IllegalArgumentException if the file cannot be saved
	 */

	public void exportSchedule(String filename) {

		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		} catch (IOException io) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}

	}

	/**
	 * Gets the course that has the same name and section as provided
	 * 
	 * @param name    name of the course
	 * @param section section number
	 * @return a course with the same name and section as the parameters
	 */
	public Course getCourseFromCatalog(String name, String section) {

		for (int i = 0; i < courseCatalog.size(); i++) {
			if (courseCatalog.get(i).getName().equals(name) && courseCatalog.get(i).getSection().equals(section)) {
				return courseCatalog.get(i);
			}
		}

		return null;
	}

	/**
	 * Adds a specific course to the user's schedule
	 * 
	 * @param name    name of course to be added
	 * @param section section number of the course to be added
	 * @return whether the course was able to be added to the user's schedule
	 * @throws IllegalArgumentException if the user is already enrolled in the
	 *                                  course
	 */

	public boolean addCourseToSchedule(String name, String section) {

		Course newCourse = getCourseFromCatalog(name, section);

		if (newCourse == null) {
			return false;
		}

		for (Activity a : schedule) {
			if (a.isDuplicate(newCourse)) {
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}

			try {
				a.checkConflict(newCourse);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}

		schedule.add(newCourse);
		return true;
	}

	/**
	 * Removes a specific course from the user's schedule
	 * 
	 * @param idx index of activity to be removed
	 * @return whether the course was removed from the user's schedule
	 */

	public boolean removeActivityFromSchedule(int idx) {

		if (idx < 0 || idx >= schedule.size()) {
			return false;
		}

		try {
			schedule.remove(idx);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}

	/**
	 * Gets the name of the schedule
	 * 
	 * @return title that the student set for his schedule
	 */

	public String getScheduleTitle() {
		return title;
	}

	/**
	 * Sets the name for the user's schedule
	 * 
	 * @param t title that the user wants to name his schedule
	 * @throws IllegalArgumentException if the title given is null
	 */

	public void setScheduleTitle(String t) {
		if (t == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		} else {
			title = t;
		}
	}

	/**
	 * Resets the user's schedule
	 */

	public void resetSchedule() {
		schedule.clear();
	}

	/**
	 * Adds a given Event to the user's schedule
	 * 
	 * @param eventTitle       title of the event
	 * @param eventMeetingDays meeting days of the event
	 * @param eventStartTime   start time of the event
	 * @param eventEndTime     end time of the event
	 * @param eventDetails     details of the event
	 * @throws IllegalArgumentException If the user already has an event with the
	 *                                  same title
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime,
			String eventDetails) {
		Event newEvent = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);

		for (Activity a : schedule) {
			if (a.isDuplicate(newEvent)) {
				throw new IllegalArgumentException("You have already created an event called " + eventTitle);

			}

			try {
				a.checkConflict(newEvent);
			} catch (ConflictException ce) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}

		schedule.add(newEvent);

	}

}
