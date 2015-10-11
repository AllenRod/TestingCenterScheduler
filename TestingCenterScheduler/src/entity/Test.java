package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Test
 * For testing only. Please Delete Afterward
 */
@Entity

public class Test implements Serializable {

	   
	@Id
	private int ID;
	private static final long serialVersionUID = 1L;

	public Test() {
		super();
	}   
	public int getID() {
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
   
}
