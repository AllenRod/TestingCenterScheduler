package entity;

import entity.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NonClassRequest
 * Inherit entity Request
 * @author CSE308 Team Five
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("AD_HOC")
public class NonClassRequest extends Request implements Serializable {
    
    private List<String> rosterList = new ArrayList<>();
    
    private static final long serialVersionUID = 1L;

    public NonClassRequest() {
	super();
    }
    
    public List<String> getRosterList() {
	return this.rosterList;
    }
    
    public void setRosterList(List<String> rosterList) {
	this.rosterList = rosterList;
    }

}
