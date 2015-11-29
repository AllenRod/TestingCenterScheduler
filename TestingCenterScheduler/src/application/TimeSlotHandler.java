package application;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import entity.Appointment;
import entity.Request;
import entity.Term;
import entity.User;

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

	// Gap time of Test Center 
	private int gapTime = 0;
	
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

	// A linkedhashmap with <Date, Integer> pair that maps the time of time slot
	// to
	// the number of available seats at that time
	// Key is the time slot with equal to or more than 0 open seat
	// Value is the number of available seats
	private LinkedHashMap<Date, Integer> numOfSeatInTimeSlot = new LinkedHashMap<>();

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
		gapTime = dbManager.R_getTestCenterInfo(term.getTermID()).getGapTime();
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
	public HashMap<Integer, Integer[]> generateTimeSlot() {
		String openHours = dbManager.R_getTestCenterInfo(term.getTermID())
				.getOpenHours();
		for (int i = 1; i <= seatNum; i++) {
			// There are total of 47 slots on hour and half-hour each day,
			// excluding 24:00. The array records the appointment request ID
			// -2 means no appointments can be made in that timeslot
			// -1 means the 30 minutes range is free
			// 0 is reserved for schedulability checking
			Integer[] appRequestIDArray = new Integer[47];
			// Initiate array by setting all timeslot request ID to -2
			for (int j = 0; j < appRequestIDArray.length; j++) {
				appRequestIDArray[j] = -2;
			}
			// Setting start time and end time
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String openTime = OpenHoursParser.getOpeningTime(openHours,
					c.get(Calendar.DAY_OF_WEEK));
			String closeTime = OpenHoursParser.getClosingTime(openHours,
					c.get(Calendar.DAY_OF_WEEK));
			int openInt = 48;
			int closeInt = -1;
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
			// Iterate through the appointment array for a single seat
			Appointment[] appArray = seatMap.get(i).getAppList();
			for (int j = 0; j < appArray.length; j++) {
				if (j < openInt || j >= closeInt)
					continue;
				if (appArray[j] == null) {
					appRequestIDArray[j] = -1;
				} else {
					appRequestIDArray[j] = appArray[j].getRequest()
							.getExamIndex();
				}
			}
			openTimeSlotMap.put(i, appRequestIDArray);
		}

		/*
		 * for (int i = 1; i <= openTimeSlotMap.size(); i++) {
		 * System.out.println(i); Integer[] hehe = openTimeSlotMap.get(i); for
		 * (int j = 0; j < hehe.length; j++) { if (hehe[j] == -1) { continue; }
		 * System.out.print(hehe[j] + " "); } }
		 */

		return openTimeSlotMap;
	}

	/**
	 * Get all possible time slot of current date for the given request
	 * 
	 * @param request
	 *            Given request
	 * @return HashMap with Time and Number of Seat value pair
	 */
	public LinkedHashMap<Date, Integer> getOpenTimeSlot(Request request) {
		openTimeSlotMap = this.generateTimeSlot();
		numOfSeatInTimeSlot.clear();
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
		// Compare with request end time
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
		duration += gapTime;
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
					if (intArray[currentSlot + i] != -1) {
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
		return numOfSeatInTimeSlot;
	}

	/**
	 * Find a seat that the student can take appointment at
	 * 
	 * @param request
	 *            Given request
	 * @param slotTime
	 *            Give time of the slot
	 * @return An available seat that avoid students taking same request in
	 *         adjacent seats
	 */
	public int getSeatNum(Request request, Date slotTime) {
		openTimeSlotMap = generateTimeSlot();
		int s = 0;
		// Get the request ID to avoid placing students in adjacent seat
		int reqID = request.getExamIndex();
		// Generate index for the time slot
		Calendar c = Calendar.getInstance();
		c.setTime(slotTime);
		int index = c.get(Calendar.HOUR_OF_DAY) * 2;
		if (c.get(Calendar.MINUTE) > 0) {
			index++;
		}
		for (int i = 1; i <= openTimeSlotMap.size(); i++) {
			Integer[] timeSlot = openTimeSlotMap.get(i);
			s = i;
			// Check if this seat is opened
			if (timeSlot[index] != -1) {
				continue;
			}
			// Check previous seat
			if (i > 1) {
				Integer[] prevSlot = openTimeSlotMap.get(i - 1);
				if (prevSlot[index] == reqID) {
					continue;
				}
			}
			// Check next seat
			if (i < openTimeSlotMap.size()) {
				Integer[] nextSlot = openTimeSlotMap.get(i + 1);
				if (nextSlot[index] == reqID) {
					continue;
				}
			}
			break;
		}
		LoggerWrapper.logger.info("Seat number " + s + " found");
		return s;
	}

	/**
	 * Check if the given appointment can be put at the given slot time
	 * 
	 * @param app
	 *            Given appointment
	 * @param slotTime
	 *            Give slot time
	 * @return message of the checking result
	 */
	public String checkAppointment(Appointment app, Date slotTime) {
		// Check for existing appointment
		if (!dbManager.S_checkAppointment(app)) {
			return "Student has already made appointment for the request";
		}
		// Get current user
		User current = app.getUser();
		// Get index of slotTime
		Calendar c = Calendar.getInstance();
		c.setTime(slotTime);
		int index = c.get(Calendar.HOUR_OF_DAY) * 2;
		if (c.get(Calendar.MINUTE) > 0) {
			index++;
		}
		// Get duration of the test in hour
		int duration = app.getRequest().getTestDuration();
		duration += gapTime;
		duration = (int) Math.ceil((double) duration / 30);
		// Iterate through each seat
		for (int i = 1; i <= seatMap.size(); i++) {
			Appointment[] appArray = seatMap.get(i).getAppList();
			// Iterate through possible overlapped time slot
			int j = index;
			do {
				if (appArray[j] == null) {
					j++;
					continue;
				}
				User user = appArray[j].getUser();
				if ((current.getNetID()).equals(user.getNetID())
						&& (current.getTerm().getTermID().equals(user.getTerm()
								.getTermID()))) {
					return "Student has made appointment in overlapped time slot";
				} else {
					j++;
					continue;
				}
			} while (j < index + duration);
		}
		return "";
	}

	/**
	 * Check if the given date is the date of TimeSlotHandler object
	 * 
	 * @param sDate
	 *            Given date
	 * @return True if the given date is the date of TimeSlotHandler object
	 */
	public boolean checkDate(Date sDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Calendar cH = Calendar.getInstance();
		cH.setTime(sDate);
		if ((c.get(Calendar.YEAR) == cH.get(Calendar.YEAR))
				&& (c.get(Calendar.DAY_OF_YEAR) == cH.get(Calendar.DAY_OF_YEAR))) {
			return true;
		}
		return false;
	}

	/**
	 * Insert appointment for the given request for schedulability checking
	 * purpose
	 * 
	 * @param request
	 *            Given request
	 * @param reqNum
	 *            The number of unmade appointments for the request
	 * @return The remaining number of unmade appointments after insertion
	 */
	public int checkInsertApp(Request request, int reqNum) {
		if (!isInRange(request.getTimeStart(), request.getTimeEnd())) {
			return reqNum;
		}
		Calendar temp = Calendar.getInstance();
		// Record if there can be anymore appointments inserted
		boolean more = true;
		// Construct a while loop to add as many appointments as possible
		do {
			// Test
			for (int i = 1; i <= openTimeSlotMap.size(); i++) {
				System.out.println();
				Integer[] hehe = openTimeSlotMap.get(i);
				for (int j = 0; j < hehe.length; j++) {
					if (hehe[j] == -2) {
						continue;
					}
					System.out.print(hehe[j] + " ");
				}
			}
			// Get new numOfSeatInTimeSlot
			numOfSeatInTimeSlot = this.getOpenTimeSlot(request);
			// Iterate through each time slot
			for (Date d : numOfSeatInTimeSlot.keySet()) {
				int seatNum = numOfSeatInTimeSlot.get(d);
				if (seatNum == 0) {
					continue;
				} else {
					// Set fake appointments for the timeslot
					temp.setTime(d);
					int index = temp.get(Calendar.HOUR_OF_DAY) * 2;
					if (temp.get(Calendar.MINUTE) > 0) {
						index++;
					}
					more = false;
					for (int i = 1; i <= seatMap.size(); i++) {
						if (seatMap.get(i).addFakeAppointment(index, request)) {
							// If the seat is open and fake appointment is
							// inserted, minus 1 from reqNum
							more = true;
							reqNum--;
							// Check if there is any more required appointment
							if (reqNum == 0) {
								// Test
								openTimeSlotMap = generateTimeSlot();
								for (int k = 1; k <= openTimeSlotMap.size(); k++) {
									System.out.println();
									Integer[] hehe = openTimeSlotMap.get(k);
									for (int j = 0; j < hehe.length; j++) {
										if (hehe[j] == -2) {
											continue;
										}
										System.out.print(hehe[j] + " ");
									}
								}
								return 0;
							}
						}
					}
				}
			}
			// If no more appointments can be inserted, break the loop and exit
			if (!more) {
				break;
			}
		} while ((numOfSeatInTimeSlot != null)
				&& (numOfSeatInTimeSlot.size() != 0));
		return reqNum;
	}

	/**
	 * Get the date of the TimeSlotHandler object
	 * 
	 * @return Date of the TimeSlotHandler object
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Check if the TimeSlotHandler is in range of the given start time and end
	 * time
	 * 
	 * @param start
	 *            Given start time
	 * @param end
	 *            Given end time
	 * @return True if the TimeSlotHandler object is in range
	 */
	private boolean isInRange(Date start, Date end) {
		Calendar cthis = Calendar.getInstance();
		Calendar ctemp = Calendar.getInstance();
		cthis.setTime(this.date);
		ctemp.setTime(start);
		if ((cthis.get(Calendar.DAY_OF_YEAR) < ctemp.get(Calendar.DAY_OF_YEAR))) {
			return false;
		}
		ctemp.setTime(end);
		if ((cthis.get(Calendar.DAY_OF_YEAR) > ctemp.get(Calendar.DAY_OF_YEAR))) {
			return false;
		}
		return true;
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
			duration += gapTime;
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

		/**
		 * Add a fake appointment to the given timeslot for a given request
		 * 
		 * @param index
		 *            Given index of timeslot
		 * @param request
		 *            Given request
		 * @return True if the appointment is successfully added to the array
		 */
		public boolean addFakeAppointment(int index, Request request) {
			// Get the duration of the appointment
			int duration = request.getTestDuration();
			duration += gapTime;
			duration = (int) Math.ceil((double) duration / 30);
			// Check if the appointment can be added to this seat at the given
			// index and duration
			for (int i = 0; i < duration; i++) {
				if (appArray[index + i] != null) {
					return false;
				}
			}
			// Set up fakeApp
			// Fake appointment used for schedulability checking
			Appointment fakeApp = new Appointment();
			fakeApp.setRequest(request);
			// Insert the appointment to timeslot for the range of appointment
			// duration
			for (int i = 0; i < duration; i++) {
				appArray[index + i] = fakeApp;
			}
			return true;
		}
	}
}
