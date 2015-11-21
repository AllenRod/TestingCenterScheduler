package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
	private TimeSlotHandler handler;

	/**
	 * Constructor for class Student
	 * 
	 * @param netID
	 *            netID of the Student
	 */
	public Student(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
		handler = null;
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
	 * @param requestID		ID of given request
	 * @return	A LinkedHashMap with Date and Map<Date, Integer> pair
	 */
	public LinkedHashMap<Date, Map<Date, Integer>> generateTimeSlot(int requestID) {
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
			allTimeSlot.put(cStart.getTime(), getOpenTimeSlot(cStart.getTime(), request));
			cStart.add(Calendar.DATE, 1);
		}
		return allTimeSlot;
	}
	
	/**
	 * 
	 * @param date
	 * @param request
	 * @return
	 */
	private LinkedHashMap<Date, Integer> getOpenTimeSlot(Date date, Request request) {
		handler = new TimeSlotHandler(date);
		handler.getTimeSlot();
		return handler.getOpenTimeSlot(request);
	}
	
	/**
	 * Make new appointment from student's input
	 * 
	 * @param course
	 *            Course of exam. If not for a course set null
	 * @param sMonth
	 *            Start month
	 * @param sDay
	 *            Start day
	 * @param sTime
	 *            Start time
	 * @param eTime
	 *            End time
	 * @return Result from making new appointment
	 */
	public String newAppointment(String course, String sMonth, String sDay,
			String sTime) { 
		try {
			String s = "";
			Calendar curC = Calendar.getInstance();
			String year = "2015"; //<<PLACEHOLDER!!!!!!!!!!!AAAAAAAAAAAA
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date requestDate = curC.getTime();
			String timeStartstr = year + "-" + sMonth + "-" + sDay + " "
					+ sTime + ":00";
			Date timeStart = (Date) formatter.parse(timeStartstr);
				Appointment r = new Appointment();
				r.setTimeStart(timeStart);
				r.setStatus(AppointmentStatus.PENDING);
				r.setSeatNum(2); //PLACEHOLDERRRRRRRRRRRRRRRRRRRRRRRR
				//Course c = dbManager.S_findCourses(course);
				Object c = null;
				if (c == null) {
					return "Course not found";
				}
				s = dbManager.loadData(r);
			return s;
		} catch (ParseException error) {
			System.out.println(error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}
}
