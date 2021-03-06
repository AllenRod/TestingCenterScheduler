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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Appointment AppointmentPK is the
 * primary key class of this entity
 * 
 * @author CSE308 Team Five
 */
@Entity
@IdClass(AppointmentPK.class)
public class Appointment implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "EXAM_ID_FK")
	private Request request;

	@Id
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "NET_ID_FK", referencedColumnName = "USER_ID"),
			@JoinColumn(name = "TERM_ID_FK", referencedColumnName = "TERM_ID")})
	private User user;

	@Column(name = "START_TIME", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStart;

	private int seatNum;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('PENDING', 'TAKEN', 'MISSED', 'SUPERFLUOUS')")
	private AppointmentStatus status;

	// if the student was emailed about this appointment yet
	private boolean emailed;

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
			case PENDING :
				return "pending";
			case TAKEN :
				return "taken";
			case MISSED :
				return "missed";
			case SUPERFLUOUS :
				return "superfluous";
		}
		return "";
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public void setIfEmailed(boolean b) {
		emailed = b;
	}

	public boolean getIfEmailed() {
		return emailed;
	}

	public enum AppointmentStatus {
		PENDING, TAKEN, MISSED, SUPERFLUOUS
	}
}
