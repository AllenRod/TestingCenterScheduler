package entity;

import entity.Request;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ClassExamRequest
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("CLASS")
public class ClassExamRequest extends Request implements Serializable {

    @ManyToOne
    @JoinColumn(name="CLASS_ID")
    private Course course;

    private static final long serialVersionUID = 1L;

    public ClassExamRequest() {
	super();
    }
    
    public Course getCourse() {
	return this.course;
    }

    public void setCourse(Course course) {
	this.course = course;
    }    
}
