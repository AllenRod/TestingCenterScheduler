package application;

public class Report {
	private String header;

	// this is where the information is added
	private StringBuilder sb;

	/**
	 * Constructor for Report class
	 * @param t					Report type
	 * @param startTermID		Start term ID
	 * @param endTermID			End term ID
	 */
	public Report(ReportType t, String startTermID, String endTermID) {
		if (t.equals(ReportType.DAY)) {
			header = "Report Type: DAY<br />Number of student appointments for each day in Term "
					+ startTermID;
		} else if (t.equals(ReportType.WEEK)) {
			header = "Report Type: WEEK<br />Number of student appointments for each week in Term"
					+ startTermID
					+ " and course identifiers for associated courses";
		} else if (t.equals(ReportType.TERM)) {
			header = "Report Type: TERM<br />Courses that use the testing center in Term "
					+ startTermID;
		} else if (t.equals(ReportType.TERM_RANGE)) {
			header = "Report Type: RANGE OF TERMS<br />Total number of student appointments in Term "
					+ startTermID + " to Term " + endTermID;
		}
		sb = new StringBuilder();
	}

	/**
	 * Adds new information to the string
	 * 
	 * @param newInfo
	 *            information to be added
	 */
	public void addToReport(String newInfo) {
		sb.append(newInfo + "<br />");
	}

	/**
	 * Return header and string generated for report
	 */
	public String toString() {
		return header + "<br />" + sb.toString() + "<br />";
	}

	public enum ReportType {
		DAY, WEEK, TERM, TERM_RANGE
	}
}
