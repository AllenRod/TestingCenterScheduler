package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Course;
import entity.Roster;
import entity.Term;
import entity.User;

/**
 * Read data from CSV file and load to the proper table.
 * 
 * @author CSE308 Team Five
 */
public class CSVLoader {
	// database manager object
	private DatabaseManager dbManager;

	// singleton object for CSVLoader
	private static CSVLoader loader = null;

	/**
	 * Constructor for class CSVLoader
	 */
	public CSVLoader() {
		dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * Load data in CSV file into the proper table
	 * 
	 * @param fileLoc
	 *            Path of the file
	 * @param table
	 *            Name of the table that the data load into
	 * @param termID
	 *            ID of term of the file to load into
	 */
	public String loadCSV(String fileLoc, String table, String termID) {
		ArrayList<Object> dataList = new ArrayList<>();
		if (!fileLoc.endsWith(".csv")) {
			return "File must be .csv file";
		}
		dbManager.createEntityManager();
		File file = new File(fileLoc);
		Scanner sc;
		Term term = dbManager.getTermByID(termID);
		if (term == null) {
			return "Term not found";
		}
		try {
			sc = new Scanner(file);
			String header = sc.nextLine();
			String[] headerVal = header.split(",");
			boolean del = true;
			if (table.toLowerCase().equals("class")) {
				if (!headerVal[0].toLowerCase().equals("classid")
						|| !headerVal[1].toLowerCase().equals("subject")
						|| !headerVal[2].toLowerCase().equals("catalognumber")
						|| !headerVal[3].toLowerCase().equals("section")
						|| !headerVal[4].toLowerCase()
								.equals("instructornetid")) {
					sc.close();
					return "Wrong csv format for Class table";
				} else {
					dbManager.delTable("Course", termID);
				}

				del = dbManager.delTable("Course", termID);
			} else if (table.toLowerCase().equals("roster")) {

				if (!headerVal[0].toLowerCase().equals("netid")
						|| !headerVal[1].toLowerCase().equals("classid")) {
					sc.close();
					return "Wrong csv format for Roster table";
				} else {
					dbManager.delTable("Roster", termID);
				}

				del = dbManager.delTable("Roster", termID);
			} else if (table.toLowerCase().equals("user")) {

				if (!headerVal[0].toLowerCase().equals("firstname")
						|| !headerVal[1].toLowerCase().equals("lastname")
						|| !headerVal[2].toLowerCase().equals("netid")
						|| !headerVal[3].toLowerCase().equals("email")) {
					sc.close();
					return "Wrong csv format for User table";
				} else {
					dbManager.delTable("User", termID);
				}

				del = dbManager.delTable("User", termID);
			} else {
				sc.close();
				return "Wrong CSV format";
			}
			if (!del) {
				sc.close();
				return "Error in deleting past data";
			}
			while (sc.hasNext()) {
				String line = sc.nextLine();
				// Guess what? A comma separates in a comma separated values
				// file
				String[] values = line.split(",");
				if (table.toLowerCase().equals("user")) {
					User user = new User();
					user.setFirstName(values[0]);
					user.setLastName(values[1]);
					user.setNetID(values[2]);
					user.setEmail(values[3]);
					user.setTerm(term);
					dataList.add(user);
				} else if (table.toLowerCase().equals("class")) {
					Course c = new Course();
					c.setClassID(values[0]);
					c.setSubject(values[1]);
					c.setCatalogNum(Integer.parseInt(values[2]));
					c.setSection(values[3]);
					c.setInstructorNetID(values[4]);
					c.setTerm(term);
					dataList.add(c);
				} else if (table.toLowerCase().equals("roster")) {
					Roster r = new Roster();
					r.setUser(values[0]);
					r.setCourse(values[1]);
					r.setTerm(term);
					dataList.add(r);
				}
			}
			String str = dbManager.loadDataList(dataList);
			sc.close();
			return str;
		} catch (Exception e) {
			return e.getClass() + ":" + e.getMessage();
		} finally {
			// Close entity manager
			dbManager.closeEntityManager();
		}
	}

	/**
	 * Return a singleton of CSVLoader
	 * 
	 * @return a singleton of class CSVLoader
	 */
	public static CSVLoader getSingleton() {
		if (loader == null) {
			loader = new CSVLoader();
		}
		return loader;
	}
}
