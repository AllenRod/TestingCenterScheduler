package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import entity.TestCenterInfo;

/**
 * Handler of Testing Center closing dates
 * 
 * @author CSE308 Team Five
 */
public class ClosedDateHandler {
	// Testing Center info object
	private TestCenterInfo tci;

	// Database Manager object
	private DatabaseManager dbManager;

	// Set<Date> to store all closed date
	private Set<Date> closedDates;

	/**
	 * Constructor of ClosedDateHandler class
	 * 
	 * @param termID
	 *            Given termID
	 */
	public ClosedDateHandler(String termID) {
		dbManager = DatabaseManager.getSingleton();
		tci = dbManager.R_getTestCenterInfo(termID);
		closedDates = parseDate();
	}

	/**
	 * Parse closing dates and reserve time into Date object and store in a set
	 * of closed dates
	 * 
	 * @return Set of closed dates
	 */
	private Set<Date> parseDate() {
		Set<Date> dateSet = new HashSet<>();
		Calendar c = Calendar.getInstance();
		Calendar cEnd = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
		try {
			// Handle closing date
			String closedDate = tci.getClosedDate();
			String[] closedDateArray = closedDate.split(";");
			for (String s : closedDateArray) {
				String[] dateString = s.split("-");
				if (dateString.length == 1) {
					// Single date
					c.setTime(formatter.parse(s));
					c.set(Calendar.YEAR, tci.getTerm().getTermYear());
					dateSet.add(c.getTime());
				} else {
					// A range of date
					c.setTime(formatter.parse(dateString[0]));
					c.set(Calendar.YEAR, tci.getTerm().getTermYear());
					cEnd.setTime(formatter.parse(dateString[1]));
					cEnd.set(Calendar.YEAR, tci.getTerm().getTermYear());
					while (!c.after(cEnd)) {
						dateSet.add(c.getTime());
						c.add(Calendar.DATE, 1);
					}
				}
			}
			// Handle reserve date
			String reserveDate = tci.getReserveTime();
			String[] reserveDateArray = reserveDate.split(";");
			for (String s : reserveDateArray) {
				String[] dateString = s.split("-");
				if (dateString.length == 1) {
					// Single date
					c.setTime(formatter.parse(s));
					c.set(Calendar.YEAR, tci.getTerm().getTermYear());
					dateSet.add(c.getTime());
				} else {
					// A range of date
					c.setTime(formatter.parse(dateString[0]));
					c.set(Calendar.YEAR, tci.getTerm().getTermYear());
					cEnd.setTime(formatter.parse(dateString[1]));
					cEnd.set(Calendar.YEAR, tci.getTerm().getTermYear());
					while (!c.after(cEnd)) {
						dateSet.add(c.getTime());
						c.add(Calendar.DATE, 1);
					}
				}
			}
		} catch (Exception error) {
			//System.out.println(error.getClass() + ":" + error.getMessage());
		}
		return dateSet;
	}

	/**
	 * Check if the given date is one of the closed dates
	 * @param current		Given date
	 * @return	If the given date is one of the closed dates
	 */
	public boolean checkClosed(Date current) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Iterator<Date> it = closedDates.iterator();
		c1.setTime(current);
		while (it.hasNext()) {
			Date thisDate = it.next();
			c2.setTime(thisDate);
			if ((c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
					&& (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))) {
				return true;
			}
		}
		return false;
	}
}
