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
	 * Calculate the total seat hour of the given request
	 * 
	 * @param request
	 *            Given request
	 * @return Total seat hour
	 */
	public int requestSeatHour(Request request) {
		int stuNum = 0;
		int gapTime = 0;
		if (request instanceof ClassExamRequest) {
			// class exam request
			stuNum = dbManager.R_getStudentNum(((ClassExamRequest) request)
					.getCourse());
			if ((Integer) stuNum == null) {
				stuNum = 0;
			}
			gapTime = dbManager.R_getGapTime(((ClassExamRequest) request)
					.getCourse().getTerm());
		} else if (request instanceof NonClassRequest) {
			// non class exam request

		}
		System.out.println("Num = " + stuNum + "; Gap Time = " + gapTime
				+ "; Sum = " + stuNum * (gapTime + request.getTestDuration()));
		return stuNum * (gapTime + request.getTestDuration());
	}

	public double calculateUtilizationDay(Term t, Date d) {
		double uti = dbManager.calculateUtilization(t, d);
		return uti;
	}

}
