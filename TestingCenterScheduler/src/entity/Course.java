package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Course
 *
 */
@Entity
public class Course implements Serializable {

	@Id
	private String classID;
	private String subject;
	private int catalogNum;
	private int section;
	private String instructorNetID;
	private static final long serialVersionUID = 1L;

	public Course() {
		super();
	}
	public String getClassID() {
		return this.classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getCatalogNum() {
		return this.catalogNum;
	}

	public void setCatalogNum(int catalogNum) {
		this.catalogNum = catalogNum;
	}
	public int getSection() {
		return this.section;
	}

	public void setSection(int section) {
		this.section = section;
	}
	public String getInstructorNetID() {
		return this.instructorNetID;
	}

	public void setInstructorNetID(String instructorNetID) {
		this.instructorNetID = instructorNetID;
	}

}
