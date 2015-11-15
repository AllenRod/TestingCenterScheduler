package application;

/**
 * Parsing open hours string in database to values for calculation
 * 
 * @author CSE308 Team Five
 */
public class OpenHoursParser {

	/**
	 * Constructor of OpenHours Parser
	 */
	private OpenHoursParser() {

	}

	/**
	 * Returns the difference between opening and closing hours for the given
	 * string on the given day. dayValue starts at 0 with Sunday
	 * 
	 * @param openHours
	 *            the long string of our open hours
	 * @param dayValue
	 *            the day of the week as an int
	 * @return the difference between the hours on that given day in minutes
	 */
	public static int getHoursDifference(String openHours, int dayValue) {
		int diff = 0;
		// split the list into each day
		String[] sepHours = openHours.split(";");
		// Sunday is 1 in Calendar.DAY_OF_WEEK but in our string is 6, all other
		// days minus 2
		if (dayValue == 1) {
			dayValue = 6;
		} else {
			dayValue -= 2;
		}
		// [0] is start time, [1] is end time
		String[] dayHours = sepHours[dayValue].split("-");
		if (dayHours[0].equals("Closed") || dayHours[1].equals("Closed")) {
			return -1;
		}
		// split into hours and mins
		String[] startTime = dayHours[0].split(":");
		String[] endTime = dayHours[1].split(":");

		// convert to mins
		int t1 = Integer.parseInt(startTime[0]) * 60
				+ Integer.parseInt(startTime[1]);
		int t2 = Integer.parseInt(endTime[0]) * 60
				+ Integer.parseInt(endTime[1]);

		diff = t2 - t1;

		return diff;
	}

	/**
	 * Returns the difference between starting time of request and closing hours
	 * for the given string on the given day. dayValue starts at 0 with Sunday
	 * 
	 * @param openHours
	 *            the long string of our open hours
	 * @param dayValue
	 *            the day of the week as an int
	 * @param RstartTime
	 *            the start time of request
	 * @return the difference between the start time and closing hours on that
	 *         given day in minutes
	 */
	public static int getHoursDifference_Start(String openHours, int dayValue,
			String RstartTime) {
		int diff = 0;
		// split the list into each day
		String[] sepHours = openHours.split(";");
		// Sunday is 1 in Calendar.DAY_OF_WEEK but in our string is 6, all other
		// days minus 2
		if (dayValue == 1) {
			dayValue = 6;
		} else {
			dayValue -= 2;
		}
		// [0] is start time, [1] is end time
		String[] dayHours = sepHours[dayValue].split("-");
		if (dayHours[0].equals("Closed") || dayHours[1].equals("Closed")) {
			return -1;
		}
		// split into hours and mins
		String[] TCstartTime = dayHours[0].split(":");
		String[] TCendTime = dayHours[1].split(":");
		String[] Rstart = RstartTime.split(":");

		// convert to mins
		int t1 = Integer.parseInt(TCstartTime[0]) * 60
				+ Integer.parseInt(TCstartTime[1]);
		int t2 = Integer.parseInt(TCendTime[0]) * 60
				+ Integer.parseInt(TCendTime[1]);
		int t3 = Integer.parseInt(Rstart[0]) * 60 + Integer.parseInt(Rstart[1]);

		if (t3 > t2) {
			// Start time is after Test center closes
			return 0;
		}

		if (t1 > t3) {
			// Test center opens after start time
			diff = t2 - t1;
		} else {
			// Start time is after Test center opens
			diff = t2 - t3;
		}

		return diff;
	}

	/**
	 * Returns the difference between starting hour and ending time of request
	 * for the given string on the given day. dayValue starts at 0 with Sunday
	 * 
	 * @param openHours
	 *            the long string of our open hours
	 * @param dayValue
	 *            the day of the week as an int
	 * @param RendTime
	 *            the ending time of request
	 * @return the difference between the starting hour and ending time on that
	 *         given day in minutes
	 */
	public static int getHoursDifference_End(String openHours, int dayValue,
			String RendTime) {
		int diff = 0;
		// split the list into each day
		String[] sepHours = openHours.split(";");
		// Sunday is 1 in Calendar.DAY_OF_WEEK but in our string is 6, all other
		// days minus 2
		if (dayValue == 1) {
			dayValue = 6;
		} else {
			dayValue -= 2;
		}
		// [0] is start time, [1] is end time
		String[] dayHours = sepHours[dayValue].split("-");
		if (dayHours[0].equals("Closed") || dayHours[1].equals("Closed")) {
			return -1;
		}
		// split into hours and mins
		String[] TCstartTime = dayHours[0].split(":");
		String[] TCendTime = dayHours[1].split(":");
		String[] Rend = RendTime.split(":");

		// convert to mins
		int t1 = Integer.parseInt(TCstartTime[0]) * 60
				+ Integer.parseInt(TCstartTime[1]);
		int t2 = Integer.parseInt(TCendTime[0]) * 60
				+ Integer.parseInt(TCendTime[1]);
		int t3 = Integer.parseInt(Rend[0]) * 60 + Integer.parseInt(Rend[1]);

		if (t3 < t1) {
			// Ending time is before Test center opens
			return 0;
		}

		if (t2 > t3) {
			// Test center closed after ending time
			diff = t3 - t1;
		} else {
			// Ending time is after Test center closed time
			diff = t2 - t1;
		}

		return diff;
	}

}
