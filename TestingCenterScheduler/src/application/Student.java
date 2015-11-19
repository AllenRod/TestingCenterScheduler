package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import entity.Course;
import entity.Appointment;
import entity.Appointment.AppointmentStatus;

/**
 * 
 * @author CSE308 Team Five
 */
public class Student {
	// database manager object
	private DatabaseManager dbManager;

	// netID of the Student
	private String netID;

	// request manager object
	private RequestManager reqManager;

	/**
	 * Constructor for class Student
	 * 
	 * @param netID
	 *            netID of the Student
	 */
	public Student(String netID) {
		this.netID = netID;
		dbManager = DatabaseManager.getSingleton();
		reqManager = RequestManager.getSingleton();
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
	 * Get all courses taken by this student
	 * 
	 * @return List of taken courses
	 */
	public List<Course> getExams() {
		return dbManager.S_getExams(netID);
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
			String sTime) { //FIXXXXXXXXXXXXX
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
				Course c = dbManager.S_findCourses(course);
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
