package edu.ncsu.csc216.wolf_scheduler.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.scheduler.WolfScheduler;

/**
 * GUI for the WolfScheduler project.  
 * The GUI displays the course catalog and the student's schedule.
 * 
 * @author Sarah Heckman
 */
public class WolfSchedulerGUI extends JFrame {

	/** ID used for object serialization */
	private static final long serialVersionUID = 1L;
	/** Panel that will contain different views for the application. */
	private JPanel panel;
	/** WolfSchedulerGUI title */
	private static final String APP_TITLE = "WolfScheduler";
	/** Constant to identify SchedulerPanel for {@link CardLayout}. */
	private static final String SCHEDULER_PANEL = "SchedulerPanel";
	/** Constant to identify SchedulePanel for {@link CardLayout}. */
	private static final String SCHEDULE_PANEL = "SchedulePanel";
	/** Scheduler panel */
	private SchedulerPanel pnlScheduler;
	/** Schedule panel */
	private SchedulePanel pnlSchedule;
	/** Reference to {@link CardLayout} for panel.  Stacks all of the panels. */
	private CardLayout cardLayout;
	/** Reference to the WolfScheduler */
	private WolfScheduler scheduler;

	/**
	 * Constructs the WolfSchedulerGUI and sets up the GUI 
	 * components.
	 */
	public WolfSchedulerGUI() {
		super();
		
		//Set general GUI info
		setSize(1000, 1000);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Construct the underlying model object
		try {			
			scheduler = new WolfScheduler(getFileName(true));
		} catch (IllegalStateException e) {
			System.exit(1);
		}
		
		//Construct panels
		pnlScheduler = new SchedulerPanel();
		pnlSchedule = new SchedulePanel();
		
		//Create JPanel that will hold the rest of the GUI information.
		//The JPanel utilizes a CardLayout, which stacks several different
		//JPanels.  User actions lead to switching which "Card" is visible.
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(pnlScheduler, SCHEDULER_PANEL);
		panel.add(pnlSchedule, SCHEDULE_PANEL);
		cardLayout.show(panel, SCHEDULER_PANEL);
		
		//Add panel to the container
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Returns a file name generated through interactions with a {@link JFileChooser}
	 * object.
	 * @param chooserType true if open, false if save
	 * @return the file name selected through {@link JFileChooser}
	 */
	private String getFileName(boolean chooserType) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
		fc.setApproveButtonText("Select");
		int returnVal = Integer.MIN_VALUE;
		if (chooserType) {
			fc.setDialogTitle("Load Course Catalog");
			returnVal = fc.showOpenDialog(this);
		} else {
			fc.setDialogTitle("Save Schedule");
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File catalogFile = fc.getSelectedFile();
		return catalogFile.getAbsolutePath();
	}

	/**
	 * Starts the Wolf Scheduler program.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new WolfSchedulerGUI();
	}
	
	/**
	 * Inner class that creates the look and behavior for the {@link JPanel} that 
	 * shows the list of requirements.
	 * 
	 * @author Sarah Heckman 
	 */
	private class SchedulerPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for adding the selected course in the catalog to the schedule */
		private JButton btnAddCourse;
		/** Button for removing the selected Course from the schedule */
		private JButton btnRemoveCourse;
		/** Button for resetting the schedule */
		private JButton btnReset;
		/** Button for displaying the final schedule */
		private JButton btnDisplay;
		/** JTable for displaying the catalog of Courses */
		private JTable tableCatalog;
		/** JTable for displaying the schdule of Courses */
		private JTable tableSchedule;
		/** TableModel for catalog */
		private CourseTableModel catalogTableModel;
		/** TableModel for schedule */
		private CourseTableModel scheduleTableModel;
		/** Student's Schedule title label */
		private JLabel lblScheduleTitle;
		/** Student's Schedule text field */
		private JTextField txtScheduleTitle;
		/** Button for setting student's schedule title */
		private JButton btnSetScheduleTitle;
		/** Border for Schedule */
		private TitledBorder borderSchedule;
		/** Panel for displaying Course Details */
		private JPanel pnlCourseDetails;
		/** Label for Course Details name title */
		private JLabel lblNameTitle = new JLabel("Name: ");
		/** Label for Course Details section title */
		private JLabel lblSectionTitle = new JLabel("Section: ");
		/** Label for Course Details title title */
		private JLabel lblTitleTitle = new JLabel("Title: ");
		/** Label for Course Details instructor title */
		private JLabel lblInstructorTitle = new JLabel("Instructor: ");
		/** Label for Course Details credit hours title */
		private JLabel lblCreditsTitle = new JLabel("Credits: ");
		/** Label for Course Details meeting title */
		private JLabel lblMeetingTitle = new JLabel("Meeting: ");
		/** Label for Course Details name */
		private JLabel lblName = new JLabel("");
		/** Label for Course Details section */
		private JLabel lblSection = new JLabel("");
		/** Label for Course Details title */
		private JLabel lblTitle = new JLabel("");
		/** Label for Course Details instructor */
		private JLabel lblInstructor = new JLabel("");
		/** Label for Course Details credit hours */
		private JLabel lblCredits = new JLabel("");
		/** Label for Course Details meeting */
		private JLabel lblMeeting = new JLabel("");
		/** Label for event title */
		private JLabel lblEventTitle = new JLabel("Event Title: ");
		/** Label for event meeting days */
		private JLabel lblEventMeetingDays = new JLabel("Meeting Days: ");
		/** Label for event start time */
		private JLabel lblEventStartTime = new JLabel("Event Start Time: ");
		/** Label for event end time */
		private JLabel lblEventEndTime = new JLabel("Event End Time: ");
		/** Label for event details */
		private JLabel lblEventDetails = new JLabel("Event Details: ");
		/** Text field for event title */
		private JTextField txtEventTitle;
		/** Check box for Sunday */
		private JCheckBox cbSunday;
		/** Check box for Monday */
		private JCheckBox cbMonday;
		/** Check box for Tuesday */
		private JCheckBox cbTuesday;
		/** Check box for Wednesday */
		private JCheckBox cbWednesday;
		/** Check box for Thursday */
		private JCheckBox cbThursday;
		/** Check box for Friday */
		private JCheckBox cbFriday;
		/** Check box for Saturday */
		private JCheckBox cbSaturday;
		/** Drop down for start hour */
		private JComboBox<Integer> comboStartHour;
		/** Drop down for start minute */
		private JComboBox<Integer> comboStartMin;
		/** Drop down for start am/pm */
		private JComboBox<String> comboStartPeriod;
		/** Drop down for end hour */
		private JComboBox<Integer> comboEndHour;
		/** Drop down for end minute */
		private JComboBox<Integer> comboEndMin;
		/** Drop down for end am/pm */
		private JComboBox<String> comboEndPeriod;
		/** Text box for details */
		private JTextField txtDetails;
		/** Button for adding event*/
		private JButton btnAddEvent;
		/** Panel for adding event */
		private JPanel pnlAddEvent;
		
		
		/**
		 * Creates the requirements list.
		 */
		public SchedulerPanel() {
			super(new GridLayout(5, 1));
			
			//Set up the JPanel that will hold action buttons		
			btnAddCourse = new JButton("Add Course");
			btnAddCourse.addActionListener(this);
			btnRemoveCourse = new JButton("Remove Activity");
			btnRemoveCourse.addActionListener(this);
			btnReset = new JButton("Reset Schedule");
			btnReset.addActionListener(this);
			btnDisplay = new JButton("Display Final Schedule");
			btnDisplay.addActionListener(this);
			lblScheduleTitle = new JLabel("Schedule Title: ");
			txtScheduleTitle = new JTextField(scheduler.getScheduleTitle(), 20); 
			btnSetScheduleTitle = new JButton("Set Title");
			btnSetScheduleTitle.addActionListener(this);
			
			JPanel pnlActions = new JPanel();
			pnlActions.setLayout(new GridLayout(3, 1));
			JPanel pnlAddRemove = new JPanel();
			pnlAddRemove.setLayout(new GridLayout(1, 2));
			pnlAddRemove.add(btnAddCourse);
			pnlAddRemove.add(btnRemoveCourse);
			JPanel pnlResetDisplay = new JPanel();
			pnlResetDisplay.setLayout(new GridLayout(1, 2));
			pnlResetDisplay.add(btnReset);
			pnlResetDisplay.add(btnDisplay);
			JPanel pnlScheduleTitle = new JPanel();
			pnlScheduleTitle.setLayout(new GridLayout(1, 3));
			pnlScheduleTitle.add(lblScheduleTitle);
			pnlScheduleTitle.add(txtScheduleTitle);
			pnlScheduleTitle.add(btnSetScheduleTitle);
			pnlActions.add(pnlAddRemove);
			pnlActions.add(pnlResetDisplay);
			pnlActions.add(pnlScheduleTitle);
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder borderActions = BorderFactory.createTitledBorder(lowerEtched, "Actions");
			pnlActions.setBorder(borderActions);
			pnlActions.setToolTipText("Scheduler Actions");
						
			//Set up Catalog table
			catalogTableModel = new CourseTableModel(true);
			tableCatalog = new JTable(catalogTableModel) {
				private static final long serialVersionUID = 1L;
				
				/**
				 * Set custom tool tips for cells
				 * @param e MouseEvent that causes the tool tip
				 * @return tool tip text
				 */
				public String getToolTipText(MouseEvent e) {
					java.awt.Point p = e.getPoint();
					int rowIndex = rowAtPoint(p);
					int colIndex = columnAtPoint(p);
					int realColumnIndex = convertColumnIndexToModel(colIndex);
					
					return (String)catalogTableModel.getValueAt(rowIndex, realColumnIndex);
				}
			};
			tableCatalog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableCatalog.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableCatalog.setFillsViewportHeight(true);
			tableCatalog.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					String name = tableCatalog.getValueAt(tableCatalog.getSelectedRow(), 0).toString();
					String section = tableCatalog.getValueAt(tableCatalog.getSelectedRow(), 1).toString();
					Course c = scheduler.getCourseFromCatalog(name, section);
					updateCourseDetails(c);
				}
				
			});
			
			JScrollPane scrollCatalog = new JScrollPane(tableCatalog);
			
			TitledBorder borderCatalog = BorderFactory.createTitledBorder(lowerEtched, "Course Catalog");
			scrollCatalog.setBorder(borderCatalog);
			scrollCatalog.setToolTipText("Course Catalog");
			
			//Set up Schedule table
			scheduleTableModel = new CourseTableModel(false);
			tableSchedule = new JTable(scheduleTableModel);
			tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableSchedule.setFillsViewportHeight(true);
			
			JScrollPane scrollSchedule = new JScrollPane(tableSchedule);
			
			borderSchedule = BorderFactory.createTitledBorder(lowerEtched, scheduler.getScheduleTitle());
			scrollSchedule.setBorder(borderSchedule);
			scrollSchedule.setToolTipText(scheduler.getScheduleTitle());
			
			updateTables();
			
			//Set up the course details panel
			pnlCourseDetails = new JPanel();
			pnlCourseDetails.setLayout(new GridLayout(4, 1));
			JPanel pnlName = new JPanel(new GridLayout(1, 4));
			pnlName.add(lblNameTitle);
			pnlName.add(lblName);
			pnlName.add(lblSectionTitle);
			pnlName.add(lblSection);
			
			JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
			pnlTitle.add(lblTitleTitle);
			pnlTitle.add(lblTitle);
			
			JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
			pnlInstructor.add(lblInstructorTitle);
			pnlInstructor.add(lblInstructor);
			pnlInstructor.add(lblCreditsTitle);
			pnlInstructor.add(lblCredits);
			
			JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
			pnlMeeting.add(lblMeetingTitle);
			pnlMeeting.add(lblMeeting);
			
			pnlCourseDetails.add(pnlName);
			pnlCourseDetails.add(pnlTitle);
			pnlCourseDetails.add(pnlInstructor);
			pnlCourseDetails.add(pnlMeeting);
			
			TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
			pnlCourseDetails.setBorder(borderCourseDetails);
			pnlCourseDetails.setToolTipText("Course Details");
			
			//Set up add event panel
			pnlAddEvent = new JPanel();
			pnlAddEvent.setLayout(new GridLayout(4, 1));
			
			JPanel pnlEventTitle = new JPanel(new GridLayout(1, 4));
			txtEventTitle = new JTextField();
			pnlEventTitle.add(lblEventTitle);
			pnlEventTitle.add(txtEventTitle);
			
			JPanel pnlDays = new JPanel(new GridLayout(1, 15));
			pnlDays.add(lblEventMeetingDays);
			pnlDays.add(new JLabel("Sun"));
			cbSunday = new JCheckBox();
			pnlDays.add(cbSunday);
			pnlDays.add(new JLabel("Mon"));
			cbMonday = new JCheckBox();
			pnlDays.add(cbMonday);
			pnlDays.add(new JLabel("Tue"));
			cbTuesday = new JCheckBox();
			pnlDays.add(cbTuesday);
			pnlDays.add(new JLabel("Wed"));
			cbWednesday = new JCheckBox();
			pnlDays.add(cbWednesday);
			pnlDays.add(new JLabel("Thu"));
			cbThursday = new JCheckBox();
			pnlDays.add(cbThursday);
			pnlDays.add(new JLabel("Fri"));
			cbFriday = new JCheckBox();
			pnlDays.add(cbFriday);
			pnlDays.add(new JLabel("Sat"));
			cbSaturday = new JCheckBox();
			pnlDays.add(cbSaturday);
			
			JPanel pnlTime = new JPanel(new GridLayout(1, 2));
			JPanel pnlStartTime = new JPanel(new GridLayout(1, 4));
			JPanel pnlEndTime = new JPanel(new GridLayout(1, 4));
			
			comboStartHour = new JComboBox<Integer>();
			comboStartHour.addItem(1);
			comboStartHour.addItem(2);
			comboStartHour.addItem(3);
			comboStartHour.addItem(4);
			comboStartHour.addItem(5);
			comboStartHour.addItem(6);
			comboStartHour.addItem(7);
			comboStartHour.addItem(8);
			comboStartHour.addItem(9);
			comboStartHour.addItem(10);
			comboStartHour.addItem(11);
			comboStartHour.addItem(12);
			comboStartMin = new JComboBox<Integer>();
			comboStartMin.addItem(0);
			comboStartMin.addItem(5);
			comboStartMin.addItem(10);
			comboStartMin.addItem(15);
			comboStartMin.addItem(20);
			comboStartMin.addItem(25);
			comboStartMin.addItem(30);
			comboStartMin.addItem(35);
			comboStartMin.addItem(40);
			comboStartMin.addItem(45);
			comboStartMin.addItem(50);
			comboStartMin.addItem(55);
			comboStartPeriod = new JComboBox<String>();
			comboStartPeriod.addItem("AM");
			comboStartPeriod.addItem("PM");
			
			pnlStartTime.add(lblEventStartTime);
			pnlStartTime.add(comboStartHour);
			pnlStartTime.add(comboStartMin);
			pnlStartTime.add(comboStartPeriod);
			
			comboEndHour = new JComboBox<Integer>();
			comboEndHour.addItem(1);
			comboEndHour.addItem(2);
			comboEndHour.addItem(3);
			comboEndHour.addItem(4);
			comboEndHour.addItem(5);
			comboEndHour.addItem(6);
			comboEndHour.addItem(7);
			comboEndHour.addItem(8);
			comboEndHour.addItem(9);
			comboEndHour.addItem(10);
			comboEndHour.addItem(11);
			comboEndHour.addItem(12);
			comboEndMin = new JComboBox<Integer>();
			comboEndMin.addItem(0);
			comboEndMin.addItem(5);
			comboEndMin.addItem(10);
			comboEndMin.addItem(15);
			comboEndMin.addItem(20);
			comboEndMin.addItem(25);
			comboEndMin.addItem(30);
			comboEndMin.addItem(35);
			comboEndMin.addItem(40);
			comboEndMin.addItem(45);
			comboEndMin.addItem(50);
			comboEndMin.addItem(55);
			comboEndPeriod = new JComboBox<String>();
			comboEndPeriod.addItem("AM");
			comboEndPeriod.addItem("PM");
			
			pnlStartTime.add(lblEventEndTime);
			pnlStartTime.add(comboEndHour);
			pnlStartTime.add(comboEndMin);
			pnlStartTime.add(comboEndPeriod);
			
			pnlTime.add(pnlStartTime);
			pnlTime.add(pnlEndTime);
			
			JPanel pnlDetails = new JPanel(new GridLayout(1, 3));
			
			txtDetails = new JTextField(20);
			btnAddEvent = new JButton("Add Event");
			btnAddEvent.addActionListener(this);
			
			pnlDetails.add(lblEventDetails);
			pnlDetails.add(txtDetails);
			pnlDetails.add(btnAddEvent);
			
			pnlAddEvent.add(pnlEventTitle);
			pnlAddEvent.add(pnlDays);
			pnlAddEvent.add(pnlTime);
			pnlAddEvent.add(pnlDetails);
			
			TitledBorder borderAddEvent = BorderFactory.createTitledBorder(lowerEtched, "Add Event");
			pnlAddEvent.setBorder(borderAddEvent);
			pnlAddEvent.setToolTipText("AddEvent");
			
			add(scrollCatalog);
			add(pnlActions);
			add(scrollSchedule);
			add(pnlCourseDetails);
			add(pnlAddEvent);
		}

		/**
		 * Performs an action based on the given {@link ActionEvent}.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddCourse) {
				int row = tableCatalog.getSelectedRow();
				if (row == -1  || row == tableCatalog.getRowCount()) {
					JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "No course selected in the catalog.");
				} else {
					try {
						if (!scheduler.addCourseToSchedule(tableCatalog.getValueAt(row, 0).toString(), tableCatalog.getValueAt(row, 1).toString())) {
							JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "Course doesn't exist.");
						}
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, iae.getMessage());
					}
				}
				updateTables();
			} else if (e.getSource() == btnRemoveCourse) {
				int row = tableSchedule.getSelectedRow();
				if (row == -1 || row == tableSchedule.getRowCount()) {
					JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "No item selected in the schedule.");
				} else {
					scheduler.removeActivityFromSchedule(row);
				}
				updateTables();
			} else if (e.getSource() == btnReset) {
				scheduler.resetSchedule();
				updateTables();
			} else if (e.getSource() == btnDisplay) {
				cardLayout.show(panel, SCHEDULE_PANEL);
				pnlSchedule.updateFinalizedTable();
			} else if (e.getSource() == btnSetScheduleTitle) {
				try {
					scheduler.setScheduleTitle(txtScheduleTitle.getText()); 
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "Invalid title.");
				}
				borderSchedule.setTitle(scheduler.getScheduleTitle());
			} else if (e.getSource() == btnAddEvent) {
				try {
					String eventMeetingDays = "";
					if (cbSunday.isSelected()) {
						eventMeetingDays += "U";
					}
					if (cbMonday.isSelected()) {
						eventMeetingDays += "M";
					}
					if (cbTuesday.isSelected()) {
						eventMeetingDays += "T";
					}
					if (cbWednesday.isSelected()) {
						eventMeetingDays += "W";
					}
					if (cbThursday.isSelected()) {
						eventMeetingDays += "H";
					}
					if (cbFriday.isSelected()) {
						eventMeetingDays += "F";
					}
					if (cbSaturday.isSelected()) {
						eventMeetingDays += "S";
					}
					if (eventMeetingDays.length() == 0) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event must occur on at least one day.");
						return;
					}
					
					int eventStartTime = 0;
					int hourIdx = comboStartHour.getSelectedIndex();
					if (hourIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					eventStartTime = comboStartHour.getItemAt(hourIdx) * 100;
					int minIdx = comboStartMin.getSelectedIndex();
					if (minIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					eventStartTime += comboStartMin.getItemAt(minIdx);
					int periodIdx = comboStartPeriod.getSelectedIndex();
					if (periodIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					if (comboStartPeriod.getItemAt(periodIdx).equals("PM") && eventStartTime < 1200) {
						eventStartTime += 1200;
					}
					
					int eventEndTime = 0;
					hourIdx = comboEndHour.getSelectedIndex();
					if (hourIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					eventEndTime = comboEndHour.getItemAt(hourIdx) * 100;
					minIdx = comboEndMin.getSelectedIndex();
					if (minIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					eventEndTime += comboEndMin.getItemAt(minIdx);
					periodIdx = comboEndPeriod.getSelectedIndex();
					if (periodIdx == -1) {
						JOptionPane.showMessageDialog(WolfSchedulerGUI.this, "The event times are invalid.");
						return;
					}
					if (comboEndPeriod.getItemAt(periodIdx).equals("PM") && eventEndTime < 1200) {
						eventEndTime += 1200;
					}
					
					scheduler.addEventToSchedule(txtEventTitle.getText(), eventMeetingDays, eventStartTime, eventEndTime, txtDetails.getText());
					
					resetEvent();
					updateTables();
				} catch (IllegalArgumentException iae) {
					String message = iae.getMessage();
					if (message == null || "".equals(message)) {
						message = "The event is invalid";
					}
					JOptionPane.showMessageDialog(WolfSchedulerGUI.this, message);
				}
			}
			
			WolfSchedulerGUI.this.repaint();
			WolfSchedulerGUI.this.validate();
		}
		
		/**
		 * Resets the fields of the add event form.
		 */
		private void resetEvent() {
			txtEventTitle.setText("");
			cbSunday.setSelected(false);
			cbMonday.setSelected(false);
			cbTuesday.setSelected(false);
			cbWednesday.setSelected(false);
			cbThursday.setSelected(false);
			cbFriday.setSelected(false);
			cbSaturday.setSelected(false);
			comboStartHour.setSelectedIndex(0);
			comboStartMin.setSelectedIndex(0);
			comboStartPeriod.setSelectedIndex(0);
			comboEndHour.setSelectedIndex(0);
			comboEndMin.setSelectedIndex(0);
			comboEndPeriod.setSelectedIndex(0);
			txtDetails.setText("");
		}
		
		/**
		 * Updates the catalog and schedule tables.
		 */
		private void updateTables() {
			catalogTableModel.updateData();
			scheduleTableModel.updateData();
		}
		
		/**
		 * Updates the pnlCourseDetails with full information about the most
		 * recently selected course.
		 * @param c course to use as source of update
		 */
		private void updateCourseDetails(Course c) {
			if (c != null) {
				lblName.setText(c.getName());
				lblSection.setText(c.getSection());
				lblTitle.setText(c.getTitle());
				lblInstructor.setText(c.getInstructorId());
				lblCredits.setText("" + c.getCredits());
				lblMeeting.setText(c.getMeetingString());
			}
		}
		
		/**
		 * {@link CourseTableModel} is the object underlying the {@link JTable} object that displays
		 * the list of {@link Course}s to the user.
		 * @author Sarah Heckman
		 */
		private class CourseTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Name", "Section", "Title", "Meeting Information"};
			/** Data stored in the table */
			private Object [][] data;
			/** Boolean flag if the model applies to the catalog or schedule */
			private boolean catalog;
			
			/**
			 * Constructs the {@link CourseTableModel} by requesting the latest information
			 * from the {@link RequirementTrackerModel}.
			 * @param catalog flag to determine if updating the catalog (true) or schedule (false)
			 */
			public CourseTableModel(boolean catalog) {
				this.catalog = catalog;
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col column index
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row row index
			 * @param col column index
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				try {
					return data[row][col];
				} catch (ArrayIndexOutOfBoundsException e) {
					return null;
				}
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row location to modify the data.
			 * @param col location to modify the data.
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with {@link Course} information from the {@link WolfScheduler}.
			 */
			private void updateData() {
				if (catalog) {
					data = scheduler.getCourseCatalog();
				} else {
					data = scheduler.getScheduledActivities();
				}
			}
		}
	}
	
	/**
	 * Inner class that creates the look and behavior for the {@link JPanel} that 
	 * shows the list of requirements.
	 * 
	 * @author Sarah Heckman 
	 */
	private class SchedulePanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for adding the selected course in the catalog to the schedule */
		private JButton btnReviseSchedule;
		/** Button for removing the selected Course from the schedule */
		private JButton btnExportSchedule;
		/** JTable for displaying the schedule of Courses */
		private JTable tableSchedule;
		/** TableModel for schedule */
		private FullCourseTableModel scheduleTableModel;
		/** Scroll pane for setting table title */
		private JScrollPane scrollSchedule;
		/** Border for schedule */
		private TitledBorder borderSchedule;
		
		/**
		 * Constructs the Schedule Panel
		 */
		public SchedulePanel() {
			super(new GridLayout(4, 1));
			
			//Set up action buttons
			btnReviseSchedule = new JButton("Revise Schedule");
			btnReviseSchedule.addActionListener(this);
			btnExportSchedule = new JButton("Export Schedule");
			btnExportSchedule.addActionListener(this);
			JPanel pnlActions = new JPanel();
			pnlActions.setLayout(new GridLayout(1, 2));
			pnlActions.add(btnReviseSchedule);
			pnlActions.add(btnExportSchedule);
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder borderActions = BorderFactory.createTitledBorder(lowerEtched, "Actions");
			pnlActions.setBorder(borderActions);
			pnlActions.setToolTipText("Schedule Actions");
			
			//Set up Schedule table
			scheduleTableModel = new FullCourseTableModel();
			tableSchedule = new JTable(scheduleTableModel);
			tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableSchedule.setFillsViewportHeight(true);
			tableSchedule.getColumnModel().getColumn(0).setPreferredWidth(30);
			tableSchedule.getColumnModel().getColumn(1).setPreferredWidth(30);
			tableSchedule.getColumnModel().getColumn(2).setPreferredWidth(250);
			tableSchedule.getColumnModel().getColumn(3).setPreferredWidth(30);
			tableSchedule.getColumnModel().getColumn(4).setPreferredWidth(50);
			tableSchedule.getColumnModel().getColumn(5).setPreferredWidth(100);
			
			scrollSchedule = new JScrollPane(tableSchedule);
			
			borderSchedule = BorderFactory.createTitledBorder(lowerEtched, scheduler.getScheduleTitle());
			scrollSchedule.setBorder(borderSchedule);
			scrollSchedule.setToolTipText(scheduler.getScheduleTitle());
			
			updateFinalizedTable();
			
			add(pnlActions);
			add(scrollSchedule);
		}
		
		/**
		 * Performs an action based on the given {@link ActionEvent}.
		 * @param e user event that triggers an action.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnReviseSchedule) {
				cardLayout.show(panel, SCHEDULER_PANEL);
			} else if (e.getSource() == btnExportSchedule) {
				try {
					String fileName = getFileName(false);
					scheduler.exportSchedule(fileName);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(WolfSchedulerGUI.this, iae.getMessage());
					String fileName = getFileName(false);
					scheduler.exportSchedule(fileName);
				} catch (IllegalStateException ise) {
					//do nothing if the window is closed.
				}
			}
		}
		
		/**
		 * Updates the finalized schedule table
		 */
		public void updateFinalizedTable() {
			scheduleTableModel.updateData();
			borderSchedule.setTitle(scheduler.getScheduleTitle());
			scrollSchedule.setToolTipText(scheduler.getScheduleTitle());
		}
		
		/**
		 * {@link FullCourseTableModel} is the object underlying the {@link JTable} object that displays
		 * the list of {@link Course}s, and all their data, to the user.
		 * @author Sarah Heckman
		 */
		private class FullCourseTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Name", "Section", "Title", "Credits", "Instructor", "Meeting Information", "Details"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the {@link CourseTableModel} by requesting the latest information
			 * from the {@link RequirementTrackerModel}.
			 */
			public FullCourseTableModel() {
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col column index
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row row index
			 * @param col column index
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				try {
					return data[row][col];
				} catch (ArrayIndexOutOfBoundsException e) {
					return null;
				}
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row location to modify the data.
			 * @param col location to modify the data.
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with {@link Course} information from the {@link WolfScheduler}.
			 */
			private void updateData() {
				data = scheduler.getFullScheduledActivities();
			}
		}
		
	}
}
