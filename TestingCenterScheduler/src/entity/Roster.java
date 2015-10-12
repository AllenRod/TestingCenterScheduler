package entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Roster
 *
 */
@Entity
@IdClass(RosterPK.class)
public class Roster implements Serializable {

    @Id
    private String netID;

    @Id
    private String classID;

    private static final long serialVersionUID = 1L;

    public Roster() {
	super();
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

}
