package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Appointment
 *
 */
@Entity
@IdClass(AppointmentPK.class)
public class Appointment implements Serializable {

    @Id
    private String examID;

    @Id
    private String netID;

    private Date timeStart;

    //@ManyToOne
    //@JoinColumn(name = "requestID")
    private int requestID;

    private int seatNum;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private static final long serialVersionUID = 1L;

    public Appointment() {
	super();
    }

    public String getExamID() {
	return examID;
    }

    public void setExamID(String examID) {
	this.examID = examID;
    }

    public String getNetID() {
	return this.netID;
    }

    public void setNetID(String netID) {
	this.netID = netID;
    }

    public Date getTimeStart() {
	return this.timeStart;
    }

    public void setTimeStart(Date timeStart) {
	this.timeStart = timeStart;
    }

    public int getRequestID() {
	return this.requestID;
    }

    public void setRequestID(int requestID) {
	this.requestID = requestID;
    }

    public int getSeatNum() {
	return this.seatNum;
    }

    public void setSeatNum(int seatNum) {
	this.seatNum = seatNum;
    }

    public AppointmentStatus getStatus() {
	return status;
    }

    public void setStatus(AppointmentStatus status) {
	this.status = status;
    }

    public enum AppointmentStatus {
	PENDING, TAKEN, MISSED
    }
}
