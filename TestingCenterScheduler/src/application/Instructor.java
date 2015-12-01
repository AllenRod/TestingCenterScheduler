package application;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.Appointment;
import entity.ClassExamRequest;
import entity.Course;
import entity.NonClassRequest;
import entity.Request;
import entity.Term;

/**
 * 
 * @author CSE308 Team Five
 */
public class Instructor {
	// database manager object
	private DatabaseManager dbManager;

	// netID of the Instructor
	private String netID;

	// request manager object
	private RequestManager reqManager;

	/**
	 * Constructor for class Instructor
	 * 
	 * @param netID
	 *            netID of the Instructor
	 */
	public Instructor(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
		reqManager = RequestManager.getSingleton();
	}

	/**
	 * Get netID of the Instructor
	 * 
	 * @return netID of the Instructor
	 */
	public String getNetID() {
		return netID;
	}

	/**
	 * Set new netID for the Instructor
	 * 
	 * @param netID
	 *            new netID of the Instructor
	 */
	public void setNetID(String netID) {
		this.netID = netID;
	}

	/**
	 * Get all class exam requests of this instructor
	 * 
	 * @return List of all class exam requests
	 */
	public List<Request> getClassExamRequests() {
		return dbManager.I_getClassExamRequests(netID);
	}

	/**
	 * Get all courses taught by this instructor
	 * 
	 * @return List of taught courses
	 */
	public List<Course> getCourses() {
		return dbManager.I_getCourses(netID);
	}

	/**
	 * Get all non class exam requests of this instructor
	 * 
	 * @return List of all non class exam requests
	 */
	public List<Request> getNonClassRequests() {
		return dbManager.I_getNonClassRequests(netID);
	}

	/**
	 * Insert the request to the database
	 * 
	 * @param request
	 *            Given request to insert
	 * @return Result from submitting new request
	 */
	public String submitRequest(Request request) {
		try {
			String s = dbManager.loadData(request);
			return s;
		} catch (Exception error) {
			return error.getClass() + ":" + error.getMessage();
		}
	}

	/**
	 * Make new request from instructor's input
	 * 
	 * @param examType
	 *            Type of exam
	 * @param course
	 *            Course of exam. If not for a course set null
	 * @param examName
	 *            Name of exam
	 * @param testDuration
	 *            Duration of exam
	 * @param sMonth
	 *            Start month
	 * @param sDay
	 *            Start day
	 * @param sTime
	 *            Start time
	 * @param eMonth
	 *            End month
	 * @param eDay
	 *            End day
	 * @param eTime
	 *            End time
	 * @param roster
	 *            Roster list of students. If exam for a course set null
	 * @return Request from the given fields
	 */
	public Request newRequest(String examType, String course, String termID,
			String examName, String testDuration, String sMonth, String sDay,
			String sTime, String eMonth, String eDay, String eTime,
			String roster) {
		try {
			Term term = dbManager.getTermByID(termID);
			Calendar curC = Calendar.getInstance();
			String year = Integer.toString(term.getTermYear());
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date requestDate = curC.getTime();
			String timeStartstr = year + "-" + sMonth + "-" + sDay + " "
					+ sTime + ":00";
			String timeEndstr = year + "-" + eMonth + "-" + eDay + " " + eTime
					+ ":00";
			Date timeStart = (Date) formatter.parse(timeStartstr);
			Date timeEnd = (Date) formatter.parse(timeEndstr);
			if (examType.equals("CLASS")) {
				ClassExamRequest r = new ClassExamRequest();
				r.setExamName(dbManager.I_setClassExamName(course));
				r.setRequestDate(requestDate);
				r.setInstructorNetID(netID);
				r.setTestDuration(Integer.parseInt(testDuration));
				r.setStatus("pending");
				r.setTimeEnd(timeEnd);
				r.setTimeStart(timeStart);
				Course c = dbManager.I_findCourse(course, term);
				r.setCourse(c);
				/*
				 * schedulable = reqManager.isSchedulable(r); if (!schedulable)
				 * { s = "Exam not schedulable"; }
				 */
				return r;
			} else if (examType.equals("AD_HOC")) {
				NonClassRequest r = new NonClassRequest();
				r.setExamName(examName);
				r.setRequestDate(requestDate);
				r.setInstructorNetID(netID);
				r.setTestDuration(Integer.parseInt(testDuration));
				r.setStatus("pending");
				r.setTimeEnd(timeEnd);
				r.setTimeStart(timeStart);
				r.setRosterList(roster);
				/*
				 * schedulable = reqManager.isSchedulable(r); if (!schedulable)
				 * { s = "Exam not schedulable"; }
				 */
				return r;
			}
			return null;
		} catch (ParseException error) {
			LoggerWrapper.logger.warning(error.getClass() + ":"
					+ error.getMessage());
			return null;
		}
	}

	/**
	 * Check the given request for schedulability and other constraints
	 * 
	 * @param newReq
	 *            Given request
	 * @return Error that the request encounters
	 */
	public String checkRequest(Request newReq) {
		if (newReq == null) {
			return "Error in making new request";
		}
		if (newReq.getTimeStart().after(newReq.getTimeEnd())) {
			return "Start date is after end date!";
		}
		if (newReq instanceof ClassExamRequest) {
			if (((ClassExamRequest) newReq).getCourse() == null) {
				return "Conflict between classID and termID, please check your input again";
			}
		}
		if (!reqManager.isSchedulable(newReq)) {
			return "Exam not schedulable";
		}
		return "";
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
	 * Edits existing request from instructor's input
	 * 
	 * @param RID
	 *            Request ID of Exam
	 * @param type
	 * @param examName
	 *            Name of exam
	 * @param testDuration
	 *            Duration of exam
	 * @param sMonth
	 *            Start month
	 * @param sDay
	 *            Start day
	 * @param sTime
	 *            Start time
	 * @param eMonth
	 *            End month
	 * @param eDay
	 *            End day
	 * @param eTime
	 *            End time
	 * @param roster
	 *            Roster list of students. If exam for a course set null
	 * @return Result from making new request
	 */
	public String editRequest(String RID, String year, String type,
			String examName, String testDuration, String sMonth, String sDay,
			String sTime, String eMonth, String eDay, String eTime,
			String roster) {
		try {
			String s = "";
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String timeStartstr = year + "-" + sMonth + "-" + sDay + " "
					+ sTime + ":00";
			String timeEndstr = year + "-" + eMonth + "-" + eDay + " " + eTime
					+ ":00";
			Date timeStart = (Date) formatter.parse(timeStartstr);
			Date timeEnd = (Date) formatter.parse(timeEndstr);
			if (timeStart.after(timeEnd)) {
				return "Start date is after end date!";
			}
			if (type.equals("CLASS")) {
				s = dbManager.I_editRequest(RID, type, examName, testDuration,
						timeStart, timeEnd, "");
			} else if (type.equals("AD_HOC")) {
				s = dbManager.I_editRequest(RID, type, examName, testDuration,
						timeStart, timeEnd, roster);
			}
			return s;
		} catch (ParseException error) {
			System.out.println(error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}

	/**
	 * Returns a list of the utilization for the request date range including
	 * the specified request if it was approved
	 * 
	 * @param request
	 *            the given request to include in calculation
	 * @return list of utilization
	 */
	public List<String> viewUtilizationWithRequest(Request request) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(request.getTimeStart());
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(request.getTimeEnd());
		endDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.MILLISECOND, 0);
		Calendar dateHolder = Calendar.getInstance();
		List<String> utiList = new ArrayList<String>();
		NumberFormat numFormat = NumberFormat.getPercentInstance();
		numFormat.setMaximumFractionDigits(3);
		for (Date d = startDate.getTime(); !startDate.after(endDate); startDate
				.add(Calendar.DATE, 1), d = startDate.getTime()) {
			dateHolder.setTime(d);
			double singleUTI = reqManager.calculateUtilizationDayWithRequest(d,
					request);
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
						+ numFormat.format(singleUTI) + "<br />";
			}
			utiList.add(s);
		}
		return utiList;
	}

	/**
	 * Edits existing request from instructor's input
	 * 
	 * @param RID
	 *            Request ID of Exam
	 * 
	 * @return Result from making new request
	 */
	public String deleteRequest(String RID) {
		return dbManager.I_deleteRequest(RID);
	}

	/**
	 * Queries DB by ExamID and returns a list of appointments
	 * 
	 * @param int ExamID number
	 * @return List<Course> List of all appointments belonging to the exam
	 */
	public List<Appointment> getAppointmentsByExamID(String EID) {
		return dbManager.getAppointmentsByExamID(Integer.parseInt(EID));
	}

	/**
	 * Queries DB by ExamID and returns a list of appointments
	 * 
	 * @param int ExamID number
	 * @return List<Course> List of all appointments belonging to the exam
	 */
	public int getStudentsInCourse(String CID) {

		return dbManager.R_getStudentNum(dbManager.getCourseByID(CID));
	}
}
