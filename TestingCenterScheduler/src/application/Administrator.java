package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import application.Report.ReportType;
import entity.Appointment;
import entity.ClassExamRequest;
import entity.Course;
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
	 * @param termID
	 *            the termID of the term the student is in
	 * @return if the student was successfully checked in
	 */
	public boolean checkInStudent(String netid, int examID, String termID) {
		return dbManager.A_checkinStudent(netid, examID, termID);
	}

	/**
	 * Generates all 4 report types for a range of terms
	 * 
	 * @param startTermID
	 *            the first termID in the range
	 * @param endTermID
	 *            the last termID in the range
	 * @return a list of reports
	 */
	public List<Report> generateReport_All(String startTermID, String endTermID) {
		List<Report> allReport = new ArrayList<Report>();
		allReport.add(generateReport_Day(startTermID));
		allReport.add(generateReport_Week(startTermID));
		allReport.add(generateReport_Term(startTermID));
		allReport.add(generateReport_TermRange(startTermID, endTermID));
		return allReport;
	}

	/**
	 * Generate report with information about the number of appointments each
	 * day
	 * 
	 * @param termID
	 *            the term for this report
	 * @return a report with information about the number of appointments each
	 *         day
	 */
	public Report generateReport_Day(String termID) {
		Report r = new Report(ReportType.DAY, termID, termID);
		Term t = dbManager.getTermByID(termID);
		Date termStart = t.getStartDate();
		Date termEnd = t.getEndDate();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(termStart);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(termEnd);
		Calendar dateHolder = Calendar.getInstance();
		for (Date d = startDate.getTime(); !startDate.after(endDate); startDate
				.add(Calendar.DATE, 1), d = startDate.getTime()) {
			dateHolder.setTime(d);
			int numApps = dbManager.R_getAppointmentOnDate(d).size();
			r.addToReport(Integer.toString(dateHolder.get(Calendar.MONTH) + 1)
					+ "/"
					+ Integer.toString(dateHolder.get(Calendar.DAY_OF_MONTH))
					+ ": " + Integer.toString(numApps));
		}
		return r;
	}

	/**
	 * Generates a report with information about the number of appointments each
	 * week and associated courses
	 * 
	 * @param termID
	 *            the term for this report
	 * @return a report with information about the number of appointments each
	 *         week and associated courses
	 */
	public Report generateReport_Week(String termID) {
		Report r = new Report(ReportType.WEEK, termID, termID);
		Term t = dbManager.getTermByID(termID);
		Date termStart = t.getStartDate();
		Date termEnd = t.getEndDate();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(termStart);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(termEnd);
		Calendar dateHolder = Calendar.getInstance();
		String buffer1 = "";
		String buffer2 = "";
		int currentWeek = startDate.get(Calendar.WEEK_OF_YEAR);
		int weekSum = 0;
		List<Course> classList = new ArrayList<Course>();
		for (Date d = startDate.getTime(); !startDate.after(endDate); startDate
				.add(Calendar.DATE, 1), d = startDate.getTime()) {
			dateHolder.setTime(d);
			int numApps = dbManager.R_getAppointmentOnDate(d).size();
			if (numApps > 0) {
				List<Request> examList = dbManager.getAllExamsByDate(d);
				for (Request e : examList) {
					Course c = ((ClassExamRequest) e).getCourse();
					if (!classList.contains(c)) {
						classList.add(c);
						buffer2 += c.getClassID() + " " + c.getSubject()
								+ c.getCatalogNum() + "_" + c.getSection()
								+ " " + c.getInstructorNetID() + ", ";
					}
				}
			}
			// in new week, output info from other week
			if (dateHolder.get(Calendar.WEEK_OF_YEAR) != currentWeek) {
				currentWeek++;
				dateHolder.add(Calendar.DATE, -7);
				buffer1 = "Week of " + (dateHolder.get(Calendar.MONTH) + 1)
						+ "/" + dateHolder.get(Calendar.DATE) + ": " + weekSum
						+ " ";
				r.addToReport(buffer1.concat(buffer2));
				weekSum = 0;
				buffer2 = "";
			} else {
				weekSum += numApps;
			}
		}

		return r;
	}

	/**
	 * Generates a report with the courses in this term
	 * 
	 * @param termID
	 *            the term for this report
	 * @return a report with the courses in this term
	 */
	public Report generateReport_Term(String termID) {
		Report r = new Report(ReportType.TERM, termID, termID);
		Term t = dbManager.getTermByID(termID);
		Date termStart = t.getStartDate();
		Date termEnd = t.getEndDate();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(termStart);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(termEnd);
		List<Request> examList = dbManager.getAllExamsBetween(termStart,
				termEnd);
		for (Request e : examList) {
			Course c = ((ClassExamRequest) e).getCourse();
			r.addToReport(c.getClassID() + " " + c.getSubject()
					+ c.getCatalogNum() + "_" + c.getSection() + " "
					+ c.getInstructorNetID());

		}
		return r;
	}

	/**
	 * Generates a report with the total number of appointments in each term in
	 * the range
	 * 
	 * @param startTermID
	 *            the first term in the range
	 * @param endTermID
	 *            the last term in the range
	 * @return a report with the total number of appointments in each term in
	 *         the range
	 */
	private Report generateReport_TermRange(String startTermID, String endTermID) {
		Report r = new Report(ReportType.TERM_RANGE, startTermID, endTermID);
		if (Integer.parseInt(startTermID) > Integer.parseInt(endTermID)) {
			r.addToReport("Starting term is after end term");
		} else {
			List<Term> termList = dbManager.getTermByRange(startTermID,
					endTermID);
			for (Term t : termList) {
				int totalApps = 0;
				String s = "Term " + t.getTerm();
				Date termStart = t.getStartDate();
				Date termEnd = t.getEndDate();
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(termStart);
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(termEnd);
				Calendar dateHolder = Calendar.getInstance();
				for (Date d = startDate.getTime(); !startDate.after(endDate); startDate
						.add(Calendar.DATE, 1), d = startDate.getTime()) {
					dateHolder.setTime(d);
					int numApps = dbManager.R_getAppointmentOnDate(d).size();
					totalApps += numApps;
				}
				r.addToReport(s.concat(" " + Integer.toString(totalApps)));
			}
		}
		return r;
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
		return dbManager.getAllTerms();
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
			return null;
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
								.get(Calendar.DAY_OF_MONTH)) + ": Closed<br />";
			} else {
				s = Integer.toString(dateHolder.get(Calendar.MONTH) + 1)
						+ "/"
						+ Integer.toString(dateHolder
								.get(Calendar.DAY_OF_MONTH)) + ": "
						+ Double.toString(singleUTI) + "<br />";
			}
			utiList.add(s);
		}
		return utiList;
	}

	/**
	 * Get list of requests
	 * 
	 * @param type
	 *            Type of request
	 * @return List of requests
	 */
	public List<Request> getRequests(String type) {
		return dbManager.getRequests(type);
	}

	/**
	 * Get list of appointments
	 * 
	 * @return List of appointments
	 */
	public List<Appointment> getAllAppointments() {
		return dbManager.getAllAppointments();
	}
}
