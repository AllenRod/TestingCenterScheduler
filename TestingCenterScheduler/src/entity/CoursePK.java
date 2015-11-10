package entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;

/**
 * Primary ID class for entity: Course
 * 
 * @author CSE308 Team Five
 */
public class CoursePK implements Serializable {

	@Column(name = "CLASS_ID")
	private String classID;

	@Column(name = "TERM_ID")
	private String term;

	private static final long serialVersionUID = 1L;

	public CoursePK() {
	}

	public String getClassID() {
		return this.classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	/*
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CoursePK)) {
			return false;
		}
		CoursePK other = (CoursePK) o;
		return true
				&& (getClassID() == null ? other.getClassID() == null
						: getClassID().equals(other.getClassID()))
				&& (getTerm() == null ? other.getTerm() == null : getTerm()
						.equals(other.getTerm()));
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 17;
		int result = 1;
		result = prime * result
				+ (getClassID() == null ? 0 : getClassID().hashCode());
		result = prime * result
				+ (getTerm() == null ? 0 : getTerm().hashCode());
		return result;
	}

}
