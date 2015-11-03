package application;

import java.util.List;

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
    
    public String newRequest(String examType, String course, String examName, String testDuration, String timeStart,
			String timeEnd) {
    	String s = "";
    	if(examType.equals("CLASS")){
	    	ClassExamRequest r = new ClassExamRequest();
	    	r.setExamName(examName);
	    	//r.setRequestDate(requestDate);
	    	r.setInstructorNetID(netID);
	    	//r.setTestDuration(testDuration);
	    	r.setStatus("pending");
	    	//r.setTimeEnd(timeEnd);
	    	//r.setTimeStart(timeStart);
	    	//r.setCourse(course);
	    	s = dbManager.loadData(r);
    	}
    	else if(examType.equals("AD_HOC")){
    		NonClassRequest r = new NonClassRequest();
	    	r.setExamName(examName);
	    	//r.setRequestDate(requestDate);
	    	r.setInstructorNetID(netID);
	    	//r.setTestDuration(testDuration);
	    	r.setStatus("pending");
	    	//r.setTimeEnd(timeEnd);
	    	//r.setTimeStart(timeStart);
	    	s = dbManager.loadData(r);
    	}
    	return s;
    }
}
