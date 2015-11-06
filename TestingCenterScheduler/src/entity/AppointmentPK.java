package entity;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * Primary key class for entity Appointment
 * @author CSE308 Team Five
 */
public class AppointmentPK implements Serializable {
    private static final long serialVersionUID = 3368854275057451457L;

    @Column(name = "EXAM_ID_FK")
    private int request;

    @Column(name = "USER_ID_FK")
    private UserPK user;
    
    public AppointmentPK() {

    }

    public int getRequest() {
	return request;
    }

    public void setRequest(int request) {
	this.request = request;
    }

    public UserPK getUserPK() {
	return user;
    }

    public void setUserPK(UserPK user) {
	this.user = user;
    }

    /*
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}
	if (!(o instanceof AppointmentPK)) {
	    return false;
	}
	AppointmentPK other = (AppointmentPK) o;
	return true && getRequest() == other.getRequest()
		&& getUserPK() == other.getUserPK();
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	final int prime = 17;
	int result = 1;
	result = prime * result
		+ (getUserPK() == null ? 0 : getUserPK().hashCode());
	result = prime * result + getRequest();
	return result;
    }
}