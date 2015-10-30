package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: UserAccount
 *
 */
@Entity
public class UserAccount implements Serializable {

    @Id
    private String netID;

    private String firstName;

    private String lastName;

    private String email;

    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'INSTRUCTOR', 'STUDENT', 'ADMIN_STUDENT',"
	    + "'ADMIN_INSTRUCTOR','INSTRUCTOR_STUDENT','ALL')")
    private UserRoles role;

    private static final long serialVersionUID = 1L;

    public UserAccount() {
	super();
    }

    public String getNetID() {
	return this.netID;
    }

    public void setNetID(String netID) {
	this.netID = netID;
    }

    public String getFirstName() {
	return this.firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return this.lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getHashedPassword() {
	return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
	this.hashedPassword = hashedPassword;
    }

    public String getRole() {
	switch (role) {
	    case ADMIN:
		return "admin";
	    case INSTRUCTOR:
		return "instr";
	    case STUDENT:
		return "student";
	    case ADMIN_INSTRUCTOR:
		return "admin_instr";
	    case ADMIN_STUDENT:
		return "admin_student";
	    case ALL:
		return "all";
	    case INSTRUCTOR_STUDENT:
		return "instr_student";
	    default:
		return "";
	}
    }

    public enum UserRoles {
	ADMIN, INSTRUCTOR, STUDENT, ADMIN_STUDENT, ADMIN_INSTRUCTOR, INSTRUCTOR_STUDENT, ALL;
    }
}
