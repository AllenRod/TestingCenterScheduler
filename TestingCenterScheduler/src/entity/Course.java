package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Course
 * 
 * @author CSE308 Team Five
 */
@Entity
@IdClass(CoursePK.class)
public class Course implements Serializable {

	@Id
	@Column(name = "CLASS_ID")
	private String classID;

	@Id
	@ManyToOne
	@JoinColumn(name = "TERM_ID")
	private Term term;

	private String subject;

	private int catalogNum;

	private String section;

	private String instructorNetID;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ClassExamRequest> request = new ArrayList<>();

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

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
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

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getInstructorNetID() {
		return this.instructorNetID;
	}

	public void setInstructorNetID(String instructorNetID) {
		this.instructorNetID = instructorNetID;
	}

	public List<ClassExamRequest> getRequest() {
		return this.request;
	}

	public void setRequest(ClassExamRequest request) {
		if (!this.request.contains(request)) {
			this.request.add(request);
		}
	}
}
