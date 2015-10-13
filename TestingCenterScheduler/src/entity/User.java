package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String netID;

    private String firstName;

    private String lastName;

    private String email;
    
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Appointment> appointment = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public User() {
	super();
    }

    public String getNetID() {
	return this.netID;
    }

    public void setNetID(String netID) {
	this.netID = netID;
    }

    public String getFirstName() {
	return this.firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return this.lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
    
    public List<Appointment> getAppointment() {
	return this.appointment;
    }
    
    public void setAppointment(List<Appointment> appointment) {
	this.appointment = appointment;
    }
}
