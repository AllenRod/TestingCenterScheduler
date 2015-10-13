package entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

/**
 * ID class for entity: Roster
 *
 */
public class RosterPK implements Serializable {

    @Column(name="USER_ID")
    private String user;

    @Column(name="CLASS_ID")
    private String course;

    private static final long serialVersionUID = 1L;

    public RosterPK() {
    }

    public String getUser() {
	return this.user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getCourse() {
	return this.course;
    }

    public void setCourse(String course) {
	this.course = course;
    }

    /*
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}
	if (!(o instanceof RosterPK)) {
	    return false;
	}
	RosterPK other = (RosterPK) o;
	return true
		&& (getUser() == null ? other.getUser() == null : getUser()
			.equals(other.getUser()))
		&& (getCourse() == null ? other.getCourse() == null
			: getCourse().equals(other.getCourse()));
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	final int prime = 17;
	int result = 1;
	result = prime * result
		+ (getUser() == null ? 0 : getUser().hashCode());
	result = prime * result
		+ (getCourse() == null ? 0 : getCourse().hashCode());
	return result;
    }

}
