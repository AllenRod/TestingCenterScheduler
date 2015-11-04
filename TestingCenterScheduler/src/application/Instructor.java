package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entity.ClassExamRequest;
import entity.Course;
import entity.NonClassRequest;
import entity.Request;

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
     * 
     * @return
     */
    public List<Request> getRequests() {
	return dbManager.I_getRequests(netID);
    }

    /**
     * 
     * @return
     */
    public List<Course> getCourses() {
	return dbManager.I_getCourses(netID);
    }

    public String newRequest(String examType, String course, String examName,
	    String testDuration, String sMonth, String sDay, String sTime,
	    String eMonth, String eDay, String eTime, List<String> roster) {
	try {
	    String s = "";
	    DateFormat formatter = new SimpleDateFormat("yyyy");
	    Calendar curC = Calendar.getInstance();
	    String year = formatter.format(curC.getTime());
	    formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	    Date requestDate = curC.getTime();
	    String timeStartstr = year + "-" + sMonth + "-" + sDay + " " + sTime + ":00";
	    String timeEndstr = year + "-" + eMonth + "-" + eDay + " " + eTime + ":00";
	    Date timeStart = (Date)formatter.parse(timeStartstr);
	    Date timeEnd = (Date)formatter.parse(timeEndstr);
	    if (examType.equals("CLASS")) {
		ClassExamRequest r = new ClassExamRequest();
		r.setExamName(examName);
		r.setRequestDate(requestDate);
		r.setInstructorNetID(netID);
		r.setTestDuration(Integer.parseInt(testDuration));
		r.setStatus("pending");
		r.setTimeEnd(timeEnd);
		r.setTimeStart(timeStart);
		r.setCourse(dbManager.I_findCourse(course, netID));
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
}
