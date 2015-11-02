package application;

import java.util.List;

import entity.Course;
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
     * @param netID	netID of the Instructor
     */
    public Instructor(String netID) {
	this.netID = netID;
	dbManager = DatabaseManager.getSingleton();
    }
    
    /**
     * Get netID of the Instructor
     * @return		netID of the Instructor
     */
    public String getNetID() {
	return netID;
    }
    
    /**
     * Set new netID for the Instructor
     * @param netID	new netID of the Instructor
     */
    public void setNetID(String netID) {
	this.netID = netID;
    }
    
    public List<Request> getRequests(){
    	return dbManager.I_getRequests(netID);
    }

	public List<Course> getCourses() {
		return dbManager.I_getCourses(netID);
	}
}
