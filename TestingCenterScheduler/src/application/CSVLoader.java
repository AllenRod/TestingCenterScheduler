package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import entity.Course;
import entity.Roster;
import entity.User;

public class CSVLoader {
    private DatabaseManager dbManager;

    public CSVLoader() {
	dbManager = DatabaseManager.getSingleton();
    }

    public void loadCSV(String fileName, String table) {
	if (!(table.toLowerCase().equals("user")
		|| table.toLowerCase().equals("class") || table.toLowerCase()
		.equals("roster"))) {
	    System.out.println("You did not enter a correct table name");
	    return;
	}
	ArrayList<Object> dataList = new ArrayList<>();
	if (!fileName.endsWith(".csv")) {
	    System.out.println("File must be .csv file");
	    return;
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
	    System.out.println("finish adding");
	    dbManager.loadDataList(dataList);
	    sc.close();
	} catch (FileNotFoundException e) {
	    System.out.println("Cannot find the file");
	}

    }
}
