package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Request
 * Provides inheritance for sub entities ClassExamRequest and NonClassRequest
 * @author CSE308 Team Five
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "REQUEST_TYPE")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_ID")
    private int examIndex;

    private String examName;

    @Column(name = "START_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStart;
    
    @Column(name = "END_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeEnd;

    private int testDuration;

    @Column(name = "REQUEST_DATE", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date requestDate;
/*
    @Column(name = "REQUEST_TYPE")
    private String requestType;
    */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING', 'APPROVED', 'DENIED', 'COMPLETED')")
    private RequestStatus status;
    
    private String instructorNetID;
    
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointment = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public Request() {
	super();
    }
    
    public String getExamName() {
    	return this.examName;
    }

    public void setExamName(String examName) {
    	this.examName = examName;
    }
    /*
    public String getRequestType() {
    	return this.requestType;
    }

    public void setRequestType(String requestType) {
    	this.requestType = requestType;
    }
    */
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
    
    public int getExamIndex(){
    	return this.examIndex;
    }
    
    public void setTestDuration(int testDuration) {
	this.testDuration = testDuration;
    }

    public Date getRequestDate() {
	return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
    }

    public String getStatus() {
	switch (status) {
	    case PENDING:
		return "pending";
	    case APPROVED:
		return "approved";
	    case DENIED:
		return "denied";
	    case COMPLETED:
		return "completed";
	}
	return "";
    }

    public void setStatus(RequestStatus status) {
	this.status = status;
    }
    public void setStatus(String status) {
    	switch (status) {
		    case "pending":
		    	this.status = RequestStatus.PENDING;
		    	break;
		    case "approved":
		    	this.status = RequestStatus.APPROVED;
		    	break;
		    case "denied":
		    	this.status = RequestStatus.DENIED;
		    	break;
		    case "completed":
		    	this.status = RequestStatus.COMPLETED;
    	}
    }
    public List<Appointment> getAppointment() {
	return this.appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
	this.appointment = appointment;
    }
    
    public String getInstructorNetID() {
    	return this.instructorNetID;
    }

    public void setInstructorNetID(String instructorNetID) {
    	this.instructorNetID = instructorNetID;
    }
    
    enum RequestStatus {
	PENDING, APPROVED, DENIED, COMPLETED
    }
}
