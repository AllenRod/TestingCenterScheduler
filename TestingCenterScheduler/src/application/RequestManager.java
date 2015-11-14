package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 * Calculate the total reserved seat min for the given request
	 * 
	 * @param request
	 *            Given request
	 * @return Total reserve seat min
	 */
	public int requestReserveSeatMin(Request request) {
		int stuNum = getStudentNum(request);
		int gapTime = (dbManager.R_getTestCenterInfo(getTerm(request))).getGapTime();
		int required = stuNum * (gapTime + request.getTestDuration());
		int i = (int)Math.ceil((double)required / 30);
		required = i * 30;
		System.out.println("Num = " + stuNum + "; Gap Time = " + gapTime
				+ "; Sum = " + required);
		return required;
	}
	
	/**
	 * Calculate the total available seat hour for the given request
	 * @param request		Given request
	 * @return	Total available seat hour for the request
	 */
	public int requestTotalSeatMin(Request request) {
		Term term = getTerm(request);
		String openHours = (dbManager.R_getTestCenterInfo(term)).getOpenHours();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		Date startTime = request.getTimeStart();
		Date endTime = request.getTimeEnd();
		c1.setTime(startTime);
		c2.setTime(endTime);
		int dayDiff = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		
		return 0;
	}

	/** 
	 * Calculate utilization for a given day in a given term
	 * 
	 * @param t
	 * @param d  NOPEEE
	 * @return
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
	 * Get the number of student taking the test from the request
	 * @param request		Request for the test
	 * @return	numbers of student taking the test
	 */
	private int getStudentNum(Request request) {
		int stuNum = 0;
		if (request instanceof ClassExamRequest) {
			// class exam request
			stuNum = dbManager.R_getStudentNum(((ClassExamRequest) request)
					.getCourse());
			if ((Integer) stuNum == null) {
				stuNum = 0;
			}
		} else if (request instanceof NonClassRequest) {
			// non class exam request
			stuNum = ((NonClassRequest) request).getRosterList().split(";").length;
		}
		return stuNum;
	}
	
	/**
	 * Get the term when the request is taking place
	 * @param request		The given request 
	 * @return	term when the request takes place
	 */
	private Term getTerm(Request request) {
		Term term = null;
		if (request instanceof ClassExamRequest) {
			term = ((ClassExamRequest) request).getCourse().getTerm();
		} else if (request instanceof NonClassRequest) {
			term = dbManager.getTermByDate(((NonClassRequest) request)
					.getTimeStart());
		}
		return term;
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


/********************************************************************
 ***TTTTTTTTTT*EEEEEEEEEE*******AAAA*******MM***********MM*FFFFFFFFFF
 *******TT*****EE**************AA**AA******MMM*********MMM*FF********
 *******TT*****EE*************AA****AA*****MMMM*******MMMM*FF*FFFFF**
 *******TT*****EEEEEEEEEE****AAAAAAAAAA****MM*MM*****MM*MM*FFF****FF*
 *******TT*****EE***********AA********AA***MM**MM***MM**MM*********FF
 *******TT*****EE**********AA**********AA**MM***MM*MM***MM*FF******FF
 *******TT*****EEEEEEEEEE*AA************AA*MM****MMM****MM**FFFFFFFF*
 ********************************************************************/
