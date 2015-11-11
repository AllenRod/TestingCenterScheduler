package entity;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Term
 * 
 * @author CSE308 Team Five
 */
@Entity
public class Term implements Serializable {

	@Id
	private String termID;

	private String termSeason;

	private int termYear;
	
	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToMany(mappedBy = "term", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Roster> roster = new ArrayList<>();
	
	@OneToMany(mappedBy = "term", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Course> course = new ArrayList<>();
	
	@OneToMany(mappedBy = "term", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<User> user = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public Term() {
		super();
	}

	public String getTermID() {
		return this.termID;
	}

	public void setTermID(String termID) {
		this.termID = termID;
	}

	public String getTermSeason() {
		return this.termSeason;
	}

	public void setTermSeason(String termSeason) {
		this.termSeason = termSeason;
	}

	public int getTermYear() {
		return this.termYear;
	}

	public void setTermYear(int termYear) {
		this.termYear = termYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Roster> getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		if (!this.roster.contains(roster)) {
			this.roster.add(roster);
		}
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		if (!this.course.contains(course)) {
			this.course.add(course);
		}
	}

	public List<User> getUser() {
		return user;
	}

	public void setRequest(User user) {
		if (!this.user.contains(user)) {
			this.user.add(user);
		}
	}

	/**
	 * Get the term information
	 * 
	 * @return Term information
	 */
	public String getTerm() {
		return this.termID + "_" + this.termSeason + "_" + this.termYear;
	}

}
