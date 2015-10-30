package entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Roster
 * RosterPK is the primary key class of this entity
 * @author CSE308 Team Five
 */
@Entity
@IdClass(RosterPK.class)
public class Roster implements Serializable {

    @Id
    private String user;

    @Id
    private String course;

    private static final long serialVersionUID = 1L;

    public Roster() {
	super();
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

}
