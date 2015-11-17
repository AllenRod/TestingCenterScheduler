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

	// A hashmap that maps the seat to its appointment list of the day
	private HashMap<Integer, AppointmentList> seatMap = new HashMap<>();

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
			AppointmentList appList = new AppointmentList();
			seatMap.put(i, appList);
		}
		setTimeSlot();
	}
	
	/**
	 * Set timeslot for the existing appointments on the current date
	 */
	private void setTimeSlot() {
		List<Appointment> appointmentsOnDate = dbManager.R_getAppointmentOnDate(date);
		for (Appointment a : appointmentsOnDate) {
			int seat = a.getSeatNum();
			AppointmentList appointmentsOnSeat = seatMap.get(seat);
			appointmentsOnSeat.setAppointment(a);
			seatMap.put(seat, appointmentsOnSeat);
		}
	}
	
	/**
	 * 
	 */
	public void getTimeSlot() {
		for (int i = 1; i <= seatNum; i++) {
			System.out.println(i + ":");
			Appointment[] appArray = seatMap.get(i).getAppList();
			for (int j = 0; j < appArray.length; j++) {
				if (appArray[j] == null) {
					System.out.print("Empty - ");
				} else {
					System.out.print(appArray[j].toString() + " - ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Inner class AppointmentList that represents the timeslot for single seat
	 * @author CSE308 Team Five
	 */
	private class AppointmentList {
		// Array of appointment for a single seat
		private Appointment[] appArray;

		/**
		 * Constructor for AppointmentList
		 */
		private AppointmentList() {
			// There are total of 47 slots on hour and half-hour each day,
			// excluding 24:00
			appArray = new Appointment[47];
		}

		/**
		 * Set appointment in the appArray
		 * @param app		Existing appointment
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
			duration = (int)Math.ceil((double) duration / 30);
			// Put the appointment in the range of time
			for (int i = 0; i < duration; i++) {
				appArray[index + i] = app;
			}
		}
		
		/**
		 * Get the current appArray
		 * @return	Current appArray
		 */
		public Appointment[] getAppList() {
			return appArray;
		}
	}
}
