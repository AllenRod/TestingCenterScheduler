package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Request
 *
 */
@Entity
public class Request implements Serializable {

    @Id
    private String requestID;

    private String classID;

    private String instructorNetID;

    private Date timeStart;

    private Date timeEnd;

    private int testDuration;

    private int numSeats;

    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private static final long serialVersionUID = 1L;

    public Request() {
	super();
    }

    public String getRequestID() {
	return this.requestID;
    }

    public void setRequestID(String requestID) {
	this.requestID = requestID;
    }

    public String getClassID() {
	return this.classID;
    }

    public void setClassID(String classID) {
	this.classID = classID;
    }

    public String getInstructorNetID() {
	return this.instructorNetID;
    }

    public void setInstructorNetID(String instructorNetID) {
	this.instructorNetID = instructorNetID;
    }

    public Date getTimeStart() {
	return this.timeStart;
    }

    public void setTimeStart(Date timeStart) {
	this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
	return this.timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
	this.timeEnd = timeEnd;
    }

    public int getTestDuration() {
	return this.testDuration;
    }

    public void setTestDuration(int testDuration) {
	this.testDuration = testDuration;
    }

    public int getNumSeats() {
	return this.numSeats;
    }

    public void setNumSeats(int numSeats) {
	this.numSeats = numSeats;
    }

    public Date getRequestDate() {
	return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
	return status;
    }

    public void setStatus(RequestStatus status) {
	this.status = status;
    }

    enum RequestStatus {
	PENDING, APPROVED, DENIED
    }
}
