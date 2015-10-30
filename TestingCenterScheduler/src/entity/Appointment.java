package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Appointment
 * AppointmentPK is the primary key class of this entity
 * @author CSE308 Team Five
 */
@Entity
@IdClass(AppointmentPK.class)
public class Appointment implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Request request;

    @Id
    @ManyToOne
    @JoinColumn(name = "NET_ID")
    private User user;

    @Column(name = "START_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStart;

    private int seatNum;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING', 'TAKEN', 'MISSED')")
    private AppointmentStatus status;

    private static final long serialVersionUID = 1L;

    public Appointment() {
	super();
    }

    public Request getRequest() {
	return request;
    }

    public void setRequest(Request request) {
	this.request = request;
    }

    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Date getTimeStart() {
	return this.timeStart;
    }

    public void setTimeStart(Date timeStart) {
	this.timeStart = timeStart;
    }

    public int getSeatNum() {
	return this.seatNum;
    }

    public void setSeatNum(int seatNum) {
	this.seatNum = seatNum;
    }

    public String getStatus() {
	switch (status) {
	    case PENDING:
		return "pending";
	    case TAKEN:
		return "taken";
	    case MISSED:
		return "missed";
	}
	return "";
    }

    public void setStatus(AppointmentStatus status) {
	this.status = status;
    }

    public enum AppointmentStatus {
	PENDING, TAKEN, MISSED
    }
}
