package entity;

import java.io.Serializable;
import java.lang.String;

/**
 * ID class for entity: Roster
 *
 */
public class RosterPK implements Serializable {

    private String netID;

    private String classID;

    private static final long serialVersionUID = 1L;

    public RosterPK() {
    }

    public String getNetID() {
	return this.netID;
    }

    public void setNetID(String netID) {
	this.netID = netID;
    }

    public String getClassID() {
	return this.classID;
    }

    public void setClassID(String classID) {
	this.classID = classID;
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
		&& (getNetID() == null ? other.getNetID() == null : getNetID()
			.equals(other.getNetID()))
		&& (getClassID() == null ? other.getClassID() == null
			: getClassID().equals(other.getClassID()));
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	final int prime = 17;
	int result = 1;
	result = prime * result
		+ (getNetID() == null ? 0 : getNetID().hashCode());
	result = prime * result
		+ (getClassID() == null ? 0 : getClassID().hashCode());
	return result;
    }

}
