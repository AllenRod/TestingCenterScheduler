package application;

import java.util.List;

import entity.Appointment;
import entity.ClassExamRequest;
import entity.Course;
import entity.NonClassRequest;
import entity.Request;
import entity.Roster;
import entity.User;

/**
 * Check superfluous appointments
 * 
 * @author CSE308 Team Five
 */
public class AppointmentChecker {
	// Singleton object of AppointmentChecker
	private static AppointmentChecker checker = null;
	
	// Single DatabaseManager object
	private DatabaseManager dbManager;
	
	/**
	 * Constructor for AppointmentChecker
	 */
	public AppointmentChecker() {
		dbManager = DatabaseManager.getSingleton();
	}
	
	/**
	 * Check in all appointments if they are superfluous and set them to the correct status
	 */
	public void checkSuperfluous() {
		List<Appointment> aList = dbManager.getAllAppointments();
		// Check every appointment
		for (Appointment a : aList) {
			User user = a.getUser();
			Request r = a.getRequest();
			if (r instanceof ClassExamRequest) {
				Course c = ((ClassExamRequest)r).getCourse();
				Roster ro = dbManager.findInRoster(user.getNetID(), c.getClassID(), c.getTerm());
				if (ro == null) {
					dbManager.setSuperfluousAppointment(a);
				}
			} else if (r instanceof NonClassRequest) {
				String rosterList = ((NonClassRequest)r).getRosterList();
				String roster[] = rosterList.split(";");
				boolean inRoster = false;
				for (String student : roster) {
					if (user.getNetID().equals(student.split(",")[0])) {
						inRoster = true;
						break;
					}
				}
				if (!inRoster) {
					dbManager.setSuperfluousAppointment(a);
				}
			}
		}
	}
	
	/**
	 * Return a singleton of AppointmentChecker
	 * 
	 * @return a singleton of class AppointmentChecker
	 */
	public static AppointmentChecker getSingleton() {
		if (checker == null) {
			checker = new AppointmentChecker();
		}
		return checker;
	}
}
