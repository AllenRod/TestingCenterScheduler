package application;

import java.io.File;
import java.util.Date;

public class Administrator {

	public Administrator() {

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
	boolean createAppointment(String examID, String netID, Date timeStart) {
		return false;

	}

	/**
	 * Imports information from a .csv file
	 * 
	 * @param f
	 *            the .csv file to be uploaded
	 * @return if the .csv file was successfully uploaded
	 */
	boolean importData(File f) {
		return false;
	}

	/**
	 * Approves a pending request
	 * 
	 * @param r
	 *            the request to be approved
	 * @return if the request was successfully approved
	 */
	boolean approveRequest(Request r) {

	}

	/**
	 * Denies a pending request
	 * 
	 * @param r
	 *            the request to be denied
	 * @return if the request was successfully denied
	 */
	boolean denyRequest(Request r) {

	}

	/**
	 * Displays all the appointments from the given info
	 */
	void viewAppointments() {

	}

	/**
	 * Changes information for a current appointment
	 * 
	 * @return if the appointment information was successfully changed
	 */
	boolean changeAppointment() {
		return false;

	}

	/**
	 * Cancels an existing appointment
	 * 
	 * @return if the appointment was successfully cancelled
	 */
	boolean cancelAppointment() {
		return false;

	}
	/**
	 * Checks in the specified student for an exam
	 * 
	 * @param netid
	 *            the net ID of the student to be checked in
	 * @return if the student was succesffuly checked in
	 */
	boolean checkInStudent(String netid) {
		return false;
	}

	/**
	 * Generates a report for the given
	 */
	Report generateReport() {

	}

	/**
	 * Displays utilization info of the testing center for the given range
	 */
	void displayUtilizationInfo() {

	}

}
