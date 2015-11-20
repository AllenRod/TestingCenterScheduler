package application;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entity.Appointment;
import entity.Request;
import entity.Term;

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

	// Term of the date of the timeslot
	private Term term = null;

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

	private HashMap<Date, Integer> numOfSeatInTimeSlot = new HashMap<>();

	/**
	 * Constructor for TimeSlotHandler
	 * 
	 * @param date
	 *            The date of the timeslot
	 */
	public TimeSlotHandler(Date date) {
		this.date = date;
		dbManager = DatabaseManager.getSingleton();
		term = dbManager.getTermByDate(date);
		seatNum = dbManager.R_getTestCenterInfo(term.getTermID()).getSeats();
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
	 * 
	 * @return The generated openTimeSlotMap
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
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String openHours = dbManager.R_getTestCenterInfo(term.getTermID())
					.getOpenHours();
			String openTime = OpenHoursParser.getOpeningTime(openHours,
					c.get(Calendar.DAY_OF_WEEK));
			String closeTime = OpenHoursParser.getClosingTime(openHours,
					c.get(Calendar.DAY_OF_WEEK));
			int openInt = -1;
			int closeInt = 48;
			if (!openTime.equals("Closed")) {
				openInt = Integer.parseInt(openTime.split(":")[0]) * 2;
				if (openTime.split(":")[1].equals("30")) {
					openInt++;
				}
			}
			if (!closeTime.equals("Closed")) {
				closeInt = Integer.parseInt(closeTime.split(":")[0]) * 2;
				if (closeTime.split(":")[1].equals("30")) {
					closeInt++;
				}
			}
			Appointment[] appArray = seatMap.get(i).getAppList();
			for (int j = 0; j < appArray.length; j++) {
				if (j < openInt || j >= closeInt)
					continue;
				if (appArray[j] == null) {
					appRequestIDArray[j] = 0;
				} else {
					appRequestIDArray[j] = appArray[j].getRequest()
							.getExamIndex();
				}
			}
			openTimeSlotMap.put(i, appRequestIDArray);
		}
		// Test
		for (java.util.Map.Entry<Integer, Integer[]> e : this.openTimeSlotMap
				.entrySet()) {
			System.out.println(e.getKey() + ":");
			for (int i = 0; i < e.getValue().length; i++) {
				if (e.getValue()[i] == -1) {
					System.out.print("+");
				} else {
					System.out.print(e.getValue()[i] + "-");
				}
			}
		}
		return openTimeSlotMap;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public HashMap<Date, Integer> getOpenTimeSlot(Request request) {
		// Get open hours of Testing Center
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String openHours = dbManager.R_getTestCenterInfo(term.getTermID())
				.getOpenHours();
		String openTime = OpenHoursParser.getOpeningTime(openHours,
				c.get(Calendar.DAY_OF_WEEK));
		String closeTime = OpenHoursParser.getClosingTime(openHours,
				c.get(Calendar.DAY_OF_WEEK));
		if (openTime.equals("Closed") || closeTime.equals("Closed")) {
			return null;
		}
		// Get open time of Testing Center
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(openTime.split(":")[0]));
		c.set(Calendar.MINUTE, Integer.parseInt(openTime.split(":")[1]));
		Date tcOpen = c.getTime();
		// Get close time of Testing Center
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(closeTime.split(":")[0]));
		c.set(Calendar.MINUTE, Integer.parseInt(closeTime.split(":")[1]));
		Date tcClose = c.getTime();
		// Compare with request start time
		Calendar cHolder = Calendar.getInstance();
		cHolder.setTime(request.getTimeStart());
		if ((c.get(Calendar.YEAR) == cHolder.get(Calendar.YEAR))
				&& (c.get(Calendar.DAY_OF_YEAR) == cHolder
						.get(Calendar.DAY_OF_YEAR))) {
			// Current day is the starting day of request
			if (request.getTimeStart().after(tcOpen)) {
				// Check if request start after Testing Center open
				if (request.getTimeStart().after(tcClose)) {
					// Check if request start after Testing Center close
					return null;
				} else {
					tcOpen = request.getTimeStart();
				}
			}
		}
		cHolder.setTime(request.getTimeEnd());
		if ((c.get(Calendar.YEAR) == cHolder.get(Calendar.YEAR))
				&& (c.get(Calendar.DAY_OF_YEAR) == cHolder
						.get(Calendar.DAY_OF_YEAR))) {
			// Current day is the ending day of request
			if (request.getTimeEnd().before(tcClose)) {
				// Check if request end before Testing Center close
				if (request.getTimeEnd().before(tcOpen)) {
					// Check if request end before Testing Center open
					return null;
				} else {
					tcClose = request.getTimeEnd();
				}
			}
		}
		// Get duration of the test in hour
		int duration = request.getTestDuration();
		duration += dbManager.R_getTestCenterInfo(term.getTermID())
				.getGapTime();
		duration = (int) Math.ceil((double) duration / 30);
		// Find number of seat open on each possible timeslot
		c.setTime(tcOpen);
		cHolder.setTime(tcClose);
		int endSlot = cHolder.get(Calendar.HOUR_OF_DAY) * 2;
		if (cHolder.get(Calendar.MINUTE) > 0) {
			endSlot++;
		}
		while (!c.after(cHolder)) {
			// Get index of current time slot
			int currentSlot = c.get(Calendar.HOUR_OF_DAY) * 2;
			if (c.get(Calendar.MINUTE) > 0) {
				currentSlot++;
			}
			if ((currentSlot + duration) > endSlot) {
				break;
			}
			int n = 0;
			// Iterate through each seat and check if the seat is opened
			for (Integer e : openTimeSlotMap.keySet()) {
				Integer[] intArray = openTimeSlotMap.get(e);
				Boolean open = true;
				for (int i = 0; i < duration; i++) {
					if (intArray[currentSlot + i] != 0) {
						open = false;
						break;
					}
				}
				if (open) {
					n++;
				}
			}
			numOfSeatInTimeSlot.put(c.getTime(), n);
			c.add(Calendar.MINUTE, 30);
		}
		// Test
		for (java.util.Map.Entry<Date, Integer> e : numOfSeatInTimeSlot
				.entrySet()) {
			System.out.println(e.getKey().toString() + ":" + e.getValue());
		}
		return numOfSeatInTimeSlot;
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
			duration += dbManager.R_getTestCenterInfo(term.getTermID())
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
