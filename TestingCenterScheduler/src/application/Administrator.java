package application;

import java.io.File;
import java.util.Date;

import entity.Request;

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
    public boolean createAppointment(String examID, String netID, Date timeStart) {
	return false;

    }

    /**
     * Imports information from a .csv file
     * 
     * @param f
     *            the .csv file to be uploaded
     * @return if the .csv file was successfully uploaded
     */
    public boolean importData(File f) {
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
     * @return if the student was succesffuly checked in
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
     * Displays utilization info of the testing center for the given range
     */
    public void displayUtilizationInfo() {

    }

}
