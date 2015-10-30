package entity;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TestCenterInfo
 *
 */
@Entity
public class TestCenterInfo implements Serializable {

    @Id
    private String term;

    private String openHours;

    private int seats;

    private int setAsideSeats;

    private String reserveTime;

    private int gapTime;

    private int reminderInterval;

    private static final long serialVersionUID = 1L;

    public TestCenterInfo() {
	super();
    }

    public String getTerm() {
	return this.term;
    }

    public void setTerm(String term) {
	this.term = term;
    }

    public String getOpenHours() {
	return this.openHours;
    }

    public void setOpenHours(String OpenHours) {
	this.openHours = OpenHours;
    }

    public int getSeats() {
	return this.seats;
    }

    public void setSeats(int seats) {
	this.seats = seats;
    }

    public int getSetAsideSeats() {
	return this.setAsideSeats;
    }

    public void setSetAsideSeats(int setAsideSeats) {
	this.setAsideSeats = setAsideSeats;
    }

    public String getReserveTime() {
	return this.reserveTime;
    }

    public void setReserveTime(String reserveTime) {
	this.reserveTime = reserveTime;
    }

    public int getGapTime() {
	return this.gapTime;
    }

    public void setGapTime(int gapTime) {
	this.gapTime = gapTime;
    }

    public int getReminderInterval() {
	return this.reminderInterval;
    }

    public void setReminderInterval(int reminderInterval) {
	this.reminderInterval = reminderInterval;
    }

}
