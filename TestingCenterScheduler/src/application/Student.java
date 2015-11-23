package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Appointment;
import entity.Appointment.AppointmentStatus;
import entity.Request;

/**
 * 
 * @author CSE308 Team Five
 */
public class Student {
	// database manager object
	private DatabaseManager dbManager;

	// netID of the Student
	private String netID;

	// TimeSlotHandler object
	private TimeSlotHandler slotHandler;
	
	// ClosedDateHandler object
	private ClosedDateHandler dateHandler;

	/**
	 * Constructor for class Student
	 * 
	 * @param netID
	 *            netID of the Student
	 */
	public Student(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
		slotHandler = null;
		dateHandler = null;
	}

	/**
	 * Get netID of the Student
	 * 
	 * @return netID of the Student
	 */
	public String getNetID() {
		return netID;
	}

	/**
	 * Set new netID for the Student
	 * 
	 * @param netID
	 *            new netID of the Student
	 */
	public void setNetID(String netID) {
		this.netID = netID;
	}

	/**
	 * Get all appointments of this student
	 * 
	 * @return List of all appointments
	 */
	public List<Appointment> getAppointments() {
		return dbManager.S_getAppointments(netID);
	}

	/**
	 * Get all requests taken by this student
	 * 
	 * @return List of taken requests
	 */
	public List<Request> getRequests() {
		return dbManager.S_getRequests(netID);
	}

	/**
	 * Generate time slots from start date to end date of the given request
	 * 
	 * @param requestID
	 *            ID of given request
	 * @return A LinkedHashMap with Date and Map<Date, Integer> pair
	 */
	public LinkedHashMap<Date, Map<Date, Integer>> generateTimeSlot(
			int requestID) {
		Request request = dbManager.S_findRequest(requestID);
		LinkedHashMap<Date, Map<Date, Integer>> allTimeSlot = new LinkedHashMap<>();
		if (request == null) {
			return null;
		}
		// Get start date and end date
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(request.getTimeStart());
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		cStart.set(Calendar.MINUTE, 0);
		cStart.set(Calendar.SECOND, 0);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(request.getTimeEnd());
		// Iterate through each day to generate open timeslot
		while (!cStart.after(cEnd)) {
			allTimeSlot.put(cStart.getTime(),
					getOpenTimeSlot(cStart.getTime(), request));
			cStart.add(Calendar.DATE, 1);
		}
		return allTimeSlot;
	}

	/**
	 * Get the open time slot of the given date for the given request
	 * 
	 * @param date
	 *            Given date
	 * @param request
	 *            Give request
	 * @return LinkedHashMap with Time and Number of open seats value pair
	 */
	private LinkedHashMap<Date, Integer> getOpenTimeSlot(Date date,
			Request request) {
		dateHandler = new ClosedDateHandler(dbManager.getTermByDate(date).getTermID());
		if (dateHandler.checkClosed(date)) {
			return null;
		}
		slotHandler = new TimeSlotHandler(date);
		slotHandler.getTimeSlot();
		return slotHandler.getOpenTimeSlot(request);
	}

	/**
	 * Make new appointment from student's input
	 * 
	 * @param requestID
	 *            ID of request
	 * @param startTime
	 *            Start time
	 * @return Result from making new appointment
	 */
	public String newAppointment(String requestID, String startTime) {
		try {
			String s = "";
			// Parse start time of appointment
			SimpleDateFormat formatter = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy");
			Date appTime = formatter.parse(startTime);
			// Get request from requestID
			Request appReq = dbManager.S_findRequest(Integer
					.parseInt(requestID));
			// Find available seat
			slotHandler = new TimeSlotHandler(appTime);
			Appointment a = new Appointment();
			a.setRequest(appReq);
			a.setTimeStart(appTime);
			a.setUser(dbManager.S_findUser(netID,
					dbManager.getTermByRequest(appReq)));
			a.setStatus(AppointmentStatus.PENDING);
			a.setSeatNum(slotHandler.getSeatNum(appReq, appTime));
			// Check appointment in timeslot
			String msg = slotHandler.checkAppointment(a, appTime);
			if (!msg.equals("")) {
				return "Fail to make appointment:\n" + msg;
			}
			s = dbManager.loadData(a);
			return s;
		} catch (Exception error) {
			return error.getClass() + ":" + error.getMessage();
		}
	}

	public boolean cancelAppointment(int examIndex, String netID, String termID) {
		return dbManager.S_cancelAppointment(examIndex, netID, termID);
	}
}
