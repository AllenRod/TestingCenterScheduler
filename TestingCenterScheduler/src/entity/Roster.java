package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Roster
 *
 */
@Entity
public class Roster implements Serializable {

	@Id
	private String studentNetID;
	private String classID;
	private static final long serialVersionUID = 1L;

	public Roster() {
		super();
	}
	public String getStudentNetID() {
		return this.studentNetID;
	}

	public void setStudentNetID(String studentNetID) {
		this.studentNetID = studentNetID;
	}
	public String getClassID() {
		return this.classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

}
