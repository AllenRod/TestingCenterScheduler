package entity;

import java.io.Serializable;

public class AppointmentPK implements Serializable {
    private String examID;

    private String netID;

    public AppointmentPK() {
	
    }

    public String getExamID() {
	return examID;
    }

    public void setExamID(String examID) {
	this.examID = examID;
    }

    public String getNetID() {
	return netID;
    }

    public void setNetID(String netID) {
	this.netID = netID;
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
	return true && getExamID() == other.getExamID() && getNetID() == other.getNetID();
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
		+ (getExamID() == null ? 0 : getExamID().hashCode());
	return result;
    }
}