package application;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entity.Appointment;

/**
 * Handle timeslot for appointments in one single day
 * 
 * @author CSE308 Team Five
 */
public class TimeSlotHandler {
	// Database manager object
	private DatabaseManager dbManager;

	// Date to handle the timeslot
	private Date date;

	// Number of seats in Test Center
	private int seatNum;

	// A hashmap with <Integer, AppointmentList> pair that maps the seat to its
	// appointment list of the day
	// Key is the seat number starting from 1
	// Value is the AppointmentTimeSlot object which holds timeslot of
	// appointments
	// for a single seat in
	private HashMap<Integer, AppointmentTimeSlot> seatMap = new HashMap<>();

	// A hashmap with <Integer, Integer[]> pair that maps the seats to its
	// appointment request ID in each timeslot of the day
	// Key is the seat number starting from 1
	// Value is an Integer array with size of 47 that records the request ID for
	// the appointments taking over the timeslots
	private HashMap<Integer, Integer[]> openTimeSlotMap = new HashMap<>();

	/**
	 * Constructor for TimeSlotHandler
	 * 
	 * @param date
	 *            The date of the timeslot
	 */
	public TimeSlotHandler(Date date) {
		this.date = date;
		dbManager = DatabaseManager.getSingleton();
		seatNum = dbManager.R_getTestCenterInfo(
				dbManager.getTermByDate(date).getTermID()).getSeats();
		for (int i = 1; i <= seatNum; i++) {
			AppointmentTimeSlot appList = new AppointmentTimeSlot();
			seatMap.put(i, appList);
		}
		setTimeSlot();
	}

	/**
	 * Set timeslot for the existing appointments on the current date
	 */
	private void setTimeSlot() {
		// Get list of appointment on the current date
		List<Appointment> appointmentsOnDate = dbManager
				.R_getAppointmentOnDate(date);
		for (Appointment a : appointmentsOnDate) {
			// Get seat number of the appointment
			int seat = a.getSeatNum();
			// Update the appointment timeslot array for the given seat number
			AppointmentTimeSlot appointmentsOnSeat = seatMap.get(seat);
			appointmentsOnSeat.setAppointment(a);
		}
	}

	/**
	 * Generate the openTimeSlotMap from existing appointments
	 * @return	The generated openTimeSlotMap 
	 */
	public HashMap<Integer, Integer[]> getTimeSlot() {
		for (int i = 1; i <= seatNum; i++) {
			// There are total of 47 slots on hour and half-hour each day,
			// excluding 24:00. The array records the appointment request ID
			// -1 means no appointments can be made in that timeslot
			// 0 means the 30 minutes range is free
			Integer[] appRequestIDArray = new Integer[47];
			// Initiate array by setting all timeslot request ID to -1
			for (int j = 0; j < appRequestIDArray.length; j++) {
				appRequestIDArray[j] = -1;
			}
			Appointment[] appArray = seatMap.get(i).getAppList();
			for (int j = 0; j < appArray.length; j++) {
				if (appArray[j] == null) {
					appRequestIDArray[j] = 0;
				} else {
					appRequestIDArray[j] = appArray[j].getRequest()
							.getExamIndex();
				}
			}
			openTimeSlotMap.put(i, appRequestIDArray);
		}
		return openTimeSlotMap;
	}

	/**
	 * Inner class AppointmentList that represents the timeslot for single seat
	 * 
	 * @author CSE308 Team Five
	 */
	private class AppointmentTimeSlot {
		// Array of appointment for a single seat in every hour ad half-hour
		// slot
		private Appointment[] appArray;

		/**
		 * Constructor for AppointmentList
		 */
		private AppointmentTimeSlot() {
			// There are total of 47 slots on hour and half-hour each day,
			// excluding 24:00
			appArray = new Appointment[47];
		}

		/**
		 * Set appointment in the appArray
		 * 
		 * @param app
		 *            Existing appointment
		 */
		public void setAppointment(Appointment app) {
			// Set the start time of appointment
			Calendar c = Calendar.getInstance();
			c.setTime(app.getTimeStart());
			// Get the index to put in appList
			int index = c.get(Calendar.HOUR_OF_DAY) * 2;
			if (c.get(Calendar.MINUTE) > 0) {
				index++;
			}
			// Get the duration of appointment(test)
			int duration = app.getRequest().getTestDuration();
			duration += dbManager.R_getTestCenterInfo(
					dbManager.getTermByRequest(app.getRequest()).getTermID())
					.getGapTime();
			duration = (int) Math.ceil((double) duration / 30);
			// Update the appointment timeslot for the range of appointment
			// duration
			for (int i = 0; i < duration; i++) {
				appArray[index + i] = app;
			}
		}

		/**
		 * Get the current appArray
		 * 
		 * @return Current appArray
		 */
		public Appointment[] getAppList() {
			return appArray;
		}
	}
}
