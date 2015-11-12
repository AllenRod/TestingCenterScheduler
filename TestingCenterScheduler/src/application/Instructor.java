package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	/**
	 * Constructor for class Instructor
	 * 
	 * @param netID
	 *            netID of the Instructor
	 */
	public Instructor(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
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
	 * @return Result from making new request
	 */
	public String newRequest(String examType, String course, String termID, String examName,
			String testDuration, String sMonth, String sDay, String sTime,
			String eMonth, String eDay, String eTime, String roster) {
		try {
			String s = "";
			DateFormat formatter = new SimpleDateFormat("yyyy");
			Calendar curC = Calendar.getInstance();
			String year = formatter.format(curC.getTime());
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
				Course c = dbManager.I_findCourse(course, termID);
				if (c == null) {
					return "Conflict between classID and termID, please check your input again";
				}
				r.setCourse(dbManager.I_findCourse(course, termID));
				// Test
				RequestManager mag = new RequestManager();
				mag.requestReserveSeatHour(r);
				s = dbManager.loadData(r);
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
				s = dbManager.loadData(r);
			}
			return s;
		} catch (ParseException error) {
			System.out.println(error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}
	
	/**
	 * Get all existing terms
	 * @return	List of existing terms
	 */
	public List<Term> getTerms() {
		return dbManager.getTerm();
	}
	/**
	 * Edits existing request from instructor's input
	 * 
	 * @param RID
	 * 			Request ID of Exam
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
	public String editRequest(String RID, String examName,
			String testDuration, String sMonth, String sDay, String sTime,
			String eMonth, String eDay, String eTime, String roster) {
		// TODO Auto-generated method stub
		
		return null;
	}
	/**
	 * Edits existing request from instructor's input
	 * 
	 * @param RID
	 * 			Request ID of Exam
	 * 
	 * @return Result from making new request
	 */
	public String deleteRequest(String RID) {
		return null;
	}
}
