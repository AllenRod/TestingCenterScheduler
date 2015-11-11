package application;

public class OpenHoursParser {

	private OpenHoursParser() {

	}

	/**
	 * Returns the difference between opening and closing hours for the given
	 * string on the given day dayValue starts at 0 with Sunday
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
		// Sunday is 0 but in string is 6, all other days minus 1
		if (dayValue == 0) {
			dayValue = 6;
		} else {
			dayValue--;
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
				+ Integer.parseInt(startTime[0]);
		int t2 = Integer.parseInt(startTime[1]) * 60
				+ Integer.parseInt(startTime[1]);

		diff = t2 - t1;

		return diff;
	}

}
