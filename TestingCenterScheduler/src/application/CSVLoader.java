package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Course;
import entity.Roster;
import entity.User;

/**
 * Read data from CSV file and load to the proper table.
 * @author CSE308 Team Five
 */
public class CSVLoader {
    private DatabaseManager dbManager;

    /**
     * Constructor for class CSVLoader
     */
    public CSVLoader() {
	dbManager = DatabaseManager.getSingleton();
    }

    /**
     * 
     * @param fileName	Path of the file
     * @param table	Name of the table that the datas load into
     */
    public String loadCSV(String fileName, String table) {
	if (!(table.toLowerCase().equals("user")
		|| table.toLowerCase().equals("class") || table.toLowerCase()
		.equals("roster"))) {
	    return "You did not enter a correct table name";
	}
	ArrayList<Object> dataList = new ArrayList<>();
	if (!fileName.endsWith(".csv")) {
	    return "File must be .csv file";
	}
	File file = new File(fileName);
	Scanner sc;
	try {
	    sc = new Scanner(file);
	    sc.nextLine(); // ignore header line
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
		    dataList.add(user);
		} else if (table.toLowerCase().equals("class")) {
		    Course c = new Course();
		    c.setClassID(values[0]);
		    c.setSubject(values[1]);
		    c.setCatalogNum(Integer.parseInt(values[2]));
		    c.setSection(values[3]);
		    c.setInstructorNetID(values[4]);
		    dataList.add(c);
		} else if (table.toLowerCase().equals("roster")) {
		    Roster r = new Roster();
		    r.setUser(values[0]);
		    r.setCourse(values[1]);
		    dataList.add(r);
		}
	    }
	    dbManager.loadDataList(dataList);
	    sc.close();
	} catch (FileNotFoundException e) {
	    System.out.println("Cannot find the file");
	}
	return "Success";

    }
}
