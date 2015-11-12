package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.Appointment;
import entity.Request;
import entity.Term;
import entity.TestCenterInfo;

/**
 * Administrator class that handles all Administrator functionality
 * 
 * @author CSE308 Team Five
 */
public class Administrator {
	// database manager object
	private DatabaseManager dbManager;

	// netID of the administrator
	private String netID;

	// Request manager
	private RequestManager reqManager;

	/**
	 * Constructor for class Administrator
	 * 
	 * @param netID
	 *            netID of the administrator
	 */
	public Administrator(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
		reqManager = RequestManager.getSingleton();
	}

	/**
	 * Creates an exam for a student with the given information
	 * 
	 * @param examID
	 *            the exam the appointment is for
	 * @param netID
	 *            the net ID of the student
	 * @param timeStart
	 *            the time at which the appointment starts
	 * @return if the appointment was successfully created
	 */
	public boolean createAppointment(String examID, String netID, Date timeStart) {
		return false;

	}

	/**
	 * Approves a pending request
	 * 
	 * @param r
	 *            the request to be approved
	 * @return if the request was successfully approved
	 */
	public boolean approveRequest(Request r) {
		return false;
	}

	/**
	 * Denies a pending request
	 * 
	 * @param r
	 *            the request to be denied
	 * @return if the request was successfully denied
	 */
	public boolean denyRequest(Request r) {
		return false;
	}

	/**
	 * Displays all the appointments from the given info
	 */
	public void viewAppointments() {

	}

	/**
	 * Changes information for a current appointment
	 * 
	 * @return if the appointment information was successfully changed
	 */
	public boolean changeAppointment() {
		return false;

	}

	/**
	 * Cancels an existing appointment
	 * 
	 * @return if the appointment was successfully cancelled
	 */
	public boolean cancelAppointment() {
		return false;

	}

	/**
	 * Checks in the specified student for an exam
	 * 
	 * @param netid
	 *            the net ID of the student to be checked in
	 * @return if the student was successfully checked in
	 */
	public boolean checkInStudent(String netid) {
		return false;
	}

	/**
	 * Generates a report for the given
	 */
	public Report generateReport() {
		return null;
	}

	/**
	 * Edit testing centers information
	 * 
	 * @param term
	 *            term of the operation of TC
	 * @param openHours
	 *            open hours of the TC
	 * @param seats
	 *            number of seats in TC
	 * @param setAsideSeats
	 *            number of set aside seats in TC
	 * @param closedDate
	 *            closing date range of TC
	 * @param reserveTime
	 *            reserve time of TC
	 * @param gapTime
	 *            gap time of TC
	 * @param reminderInterval
	 *            reminder interval of TC
	 * @return editing result from database
	 */
	public String editTestCenterInfo(String term, String openHours, int seats,
			int setAsideSeats, String closedDate, String reserveTime,
			int gapTime, int reminderInterval) {
		TestCenterInfo info = new TestCenterInfo();
		info.setTerm(dbManager.getTermByID(term));
		info.setOpenHours(openHours);
		info.setSeats(seats);
		info.setSetAsideSeats(setAsideSeats);
		info.setClosedDate(closedDate);
		info.setReserveTime(reserveTime);
		info.setGapTime(gapTime);
		info.setReminderInterval(reminderInterval);
		dbManager.A_checkTerm(term);
		return dbManager.loadData(info);
	}

	/**
	 * Get netID of the administrator
	 * 
	 * @return netID of the administrator
	 */
	public String getNetID() {
		return netID;
	}

	/**
	 * Set new netID for the administrator
	 * 
	 * @param netID
	 *            new netID of the administrator
	 */
	public void setNetID(String netID) {
		this.netID = netID;
	}

	/**
	 * Get all existing testing center info
	 * 
	 * @return List of testing center info
	 */
	public List<TestCenterInfo> getTCInfo() {
		return dbManager.A_getTCInfo();
	}

	/**
	 * Get all existing terms
	 * 
	 * @return List of existing terms
	 */
	public List<Term> getTerms() {
		return dbManager.getTerm();
	}

	/**
	 * Returns a list of the utilization for the date range
	 * 
	 * @param term
	 *            term the range is in
	 * @param startMonth
	 *            month of the start date
	 * @param startDay
	 *            day of start date
	 * @param endMonth
	 *            month of end date
	 * @param endDay
	 *            day of end date
	 * @return list of utilization
	 */
	public List<String> viewUtilization(String term, String startMonth,
			String startDay, String endMonth, String endDay) {
		int year = dbManager.getTermByID(term).getTermYear();
		Calendar startDate = Calendar.getInstance();
		startDate.set(year, Integer.parseInt(startMonth) - 1,
				Integer.parseInt(startDay), 0, 0, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.set(year, Integer.parseInt(endMonth) - 1,
				Integer.parseInt(endDay), 0, 0, 0);
		Calendar dateHolder = Calendar.getInstance();
		List<String> utiList = new ArrayList<String>();
		if (startDate.after(endDate)) {
			return utiList;
		}
		for (Date d = startDate.getTime(); !startDate.after(endDate); startDate
				.add(Calendar.DATE, 1), d = startDate.getTime()) {
			dateHolder.setTime(d);
			double singleUTI = reqManager.calculateUtilizationDay(term, d);
			String s = "";
			if (singleUTI == -1) {
				s = Integer.toString(dateHolder.get(Calendar.MONTH) + 1)
						+ "/"
						+ Integer.toString(dateHolder
								.get(Calendar.DAY_OF_MONTH)) + ": Closed";
			} else {
				s = Integer.toString(dateHolder.get(Calendar.MONTH) + 1)
						+ "/"
						+ Integer.toString(dateHolder
								.get(Calendar.DAY_OF_MONTH)) + ": "
						+ Double.toString(singleUTI);
			}
			utiList.add(s);
		}
		return utiList;
	}

	public List<Request> getRequests(String type) {
		return dbManager.getRequests(type);
	}

	public List<Appointment> getAllAppointments() {
		return null;
	}
}
