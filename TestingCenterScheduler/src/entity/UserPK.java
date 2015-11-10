package entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;

/**
 * Primary ID class for entity: User
 * 
 * @author CSE308 Team Five
 */
public class UserPK implements Serializable {

	@Column(name = "USER_ID")
	private String netID;

	@Column(name = "TERM_ID")
	private String term;

	private static final long serialVersionUID = 1L;

	public UserPK() {
	}

	public String getNetID() {
		return this.netID;
	}

	public void setNetID(String netID) {
		this.netID = netID;
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
		if (!(o instanceof UserPK)) {
			return false;
		}
		UserPK other = (UserPK) o;
		return true
				&& (getNetID() == null ? other.getNetID() == null : getNetID()
						.equals(other.getNetID()))
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
				+ (getNetID() == null ? 0 : getNetID().hashCode());
		result = prime * result
				+ (getTerm() == null ? 0 : getTerm().hashCode());
		return result;
	}

}
