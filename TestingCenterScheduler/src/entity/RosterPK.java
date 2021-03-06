package entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;

/**
 * Primary ID class for entity: Roster
 * 
 * @author CSE308 Team Five
 */
public class RosterPK implements Serializable {

	@Column(name = "USER_ID")
	private String user;

	@Column(name = "CLASS_ID")
	private String course;

	@Column(name = "TERM_ID")
	private String term;

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
		if (!(o instanceof RosterPK)) {
			return false;
		}
		RosterPK other = (RosterPK) o;
		return true
				&& (getUser() == null ? other.getUser() == null : getUser()
						.equals(other.getUser()))
				&& (getCourse() == null ? other.getCourse() == null
						: getCourse().equals(other.getCourse()))
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
				+ (getUser() == null ? 0 : getUser().hashCode());
		result = prime * result
				+ (getCourse() == null ? 0 : getCourse().hashCode());
		result = prime * result
				+ (getTerm() == null ? 0 : getTerm().hashCode());
		return result;
	}

}
