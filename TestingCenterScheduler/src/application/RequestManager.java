package application;

import java.util.Date;

import entity.ClassExamRequest;
import entity.NonClassRequest;
import entity.Request;
import entity.Term;

/**
 * Manage request schedulability and utilization.
 * 
 * @author CSE308 Team Five
 */
public class RequestManager {
	// database manager object
	private DatabaseManager dbManager;

	// single instance of RequestManager
	private static RequestManager requestManager;

	/**
	 * Constructor for RequestManager object
	 */
	public RequestManager() {
		dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * Calculate the total reserved seat hour for the given request
	 * 
	 * @param request
	 *            Given request
	 * @return Total reserve seat hour
	 */
	public int requestReserveSeatHour(Request request) {
		int stuNum = 0;
		int gapTime = 0;
		Term term = null;
		if (request instanceof ClassExamRequest) {
			// class exam request
			stuNum = dbManager.R_getStudentNum(((ClassExamRequest) request)
					.getCourse());
			if ((Integer) stuNum == null) {
				stuNum = 0;
			}
			term = ((ClassExamRequest) request).getCourse().getTerm();
			gapTime = dbManager.R_getGapTime(term);
		} else if (request instanceof NonClassRequest) {
			// non class exam request
			stuNum = ((NonClassRequest) request).getRosterList().split(";").length;
			term = dbManager.getTermByDate(((NonClassRequest) request)
					.getTimeStart());
			gapTime = dbManager.R_getGapTime(term);
		}
		System.out.println("Num = " + stuNum + "; Gap Time = " + gapTime
				+ "; Sum = " + stuNum * (gapTime + request.getTestDuration()));
		return stuNum * (gapTime + request.getTestDuration());
	}

	/**
	 * Calculate utilization for a given day in a given term
	 * 
	 * @param t
	 *            the term the day is in
	 * @param d
	 *            the date for which to calculate the utilization
	 * @return utilization for that day
	 */
	public double calculateUtilizationDay(String term, Date d) {
		double uti = dbManager.calculateUtilization(term, d);
		return uti;
	}

	/**
	 * Return a singleton of RequestManager
	 * 
	 * @return a singleton of class RequestManager
	 */
	public static RequestManager getSingleton() {
		if (requestManager == null) {
			requestManager = new RequestManager();
		}
		return requestManager;
	}

}
