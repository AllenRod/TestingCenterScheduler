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
    private String user;

    @Id
    @ManyToOne
    @JoinColumn(name="CLASS_ID")
    private Course course;

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

    public Course getCourse() {
	return this.course;
    }

    public void setCourse(Course course) {
	this.course = course;
    }

}
