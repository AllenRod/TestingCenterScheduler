package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.Appointment;
import entity.ClassExamRequest;
import entity.NonClassRequest;
import entity.Request;
import entity.Term;
import entity.TestCenterInfo;

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
	
	// ClosedDateHandler object
	private ClosedDateHandler dateHandler;

	/**
	 * Constructor for RequestManager object
	 */
	public RequestManager() {
		dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * Determine if a new request is schedulable
	 * 
	 * @param request
	 *            Given new request
	 * @return if the request is schedulable
	 */
	public boolean isSchedulable(Request request) {
		// Required seat min for this new request
		int newRequestReq = requestRequiredSeatMin(request, 0);
		// Total available seat min for this new request
		int totalAval = requestTotalSeatMin(request);
		// Find all past requests in the range of this new request
		List<Request> pastRequests = dbManager.R_getRequestBetween(
				request.getTimeStart(), request.getTimeEnd());

		return false;
	}

	/**
	 * Calculate the total required seat min for the given request
	 * 
	 * @param request
	 *            Given request
	 * @param appNum
	 *            Numbers of appointments already made for the request
	 * @return Total required seat min
	 */
	private int requestRequiredSeatMin(Request request, int appNum) {
		int stuNum = getStudentNum(request) - appNum;
		int gapTime = (dbManager.R_getTestCenterInfo(dbManager
				.getTermByRequest(request).getTermID())).getGapTime();
		int required = gapTime + request.getTestDuration();
		int i = (int) Math.ceil((double) required / 30);
		required = i * 30 * stuNum;
		System.out.println("Num = " + stuNum + "; Gap Time = " + gapTime
				+ "; Sum = " + required);
		return required;
	}

	/**
	 * Calculate the total available seat hour for the given request
	 * 
	 * @param request
	 *            Given request
	 * @return Total available seat hour for the request
	 */
	private int requestTotalSeatMin(Request request) {
		int total = 0;
		int t = 0;
		Term term = dbManager.getTermByRequest(request);
		String openHours = (dbManager.R_getTestCenterInfo(term.getTermID()))
				.getOpenHours();
		Calendar cStart = Calendar.getInstance();
		Calendar cEnd = Calendar.getInstance();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		Date startTime = request.getTimeStart();
		Date endTime = request.getTimeEnd();
		cStart.setTime(startTime);
		t = OpenHoursParser.getHoursDifference_Start(openHours,
				cStart.get(Calendar.DAY_OF_WEEK),
				timeFormatter.format(startTime));
		if (t >= 0) {
			total += t;
		}
		cEnd.setTime(endTime);
		t = OpenHoursParser.getHoursDifference_End(openHours,
				cEnd.get(Calendar.DAY_OF_WEEK), timeFormatter.format(endTime));
		if (t >= 0) {
			total += t;
		}
		// Exclude start date and end date
		cStart.add(Calendar.DATE, 1);
		while (cStart.before(cEnd)) {
			t = OpenHoursParser.getHoursDifference(openHours,
					cStart.get(Calendar.DAY_OF_WEEK));
			if (t >= 0) {
				total += t;
			}
			cStart.add(Calendar.DATE, 1);
		}
		System.out.println("Total is " + total);
		return total;
	}

	/**
	 * Calculate utilization for a given day in a given term
	 * 
	 * @param termID
	 *            Term ID
	 * @param d
	 *            the date for which to calculate the utilization
	 * @return utilization for that day
	 */
	public double calculateUtilizationDay(String termID, Date d) {
		dateHandler = new ClosedDateHandler(termID);
		if (dateHandler.checkClosed(d)) {
			return -1;
		}
		double currentDayUTI = 0;
		int durationSum = 0;
		TestCenterInfo tci = dbManager.R_getTestCenterInfo(termID);
		List<Appointment> appList = dbManager.R_getAppointmentOnDate(d);
		int gapTime = tci.getGapTime();
		for (Appointment a : appList) {
			int appLength = a.getRequest().getTestDuration() + gapTime;
			// check if it needs to be rounded up
			if (appLength % 30 != 0) {
				appLength = ((appLength / 30) + 1) * 30;
			}
			durationSum += appLength;
		}
		int openHourDuration = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		Calendar c2 = Calendar.getInstance();
		int dayVal = c.get(Calendar.DAY_OF_WEEK);
		openHourDuration = OpenHoursParser.getHoursDifference(
				tci.getOpenHours(), dayVal);
		if (openHourDuration == -1) {
			return -1;
		}
		int seatNum = tci.getSeats();
		currentDayUTI = (double) durationSum / (seatNum * openHourDuration);
		// past or current day
		if (c.get(Calendar.YEAR) <= c2.get(Calendar.YEAR)
				&& c.get(Calendar.MONTH) <= c2.get(Calendar.MONTH)
				&& c.get(Calendar.DATE) <= c2.get(Calendar.DATE)) {
			return currentDayUTI;
		}
		// future day
		double totalUTI = currentDayUTI;
		int expectedDurationSum = 0;
		List<Request> exams = dbManager.getAllExamsByDate(d);
		for (Request e : exams) {
			int expectedDuration = e.getTestDuration() + gapTime;
			int numStudentsForExam = 0;
			if (e instanceof ClassExamRequest) {
				numStudentsForExam = dbManager
						.R_getStudentNum(((ClassExamRequest) e).getCourse());
			}
			if (e instanceof NonClassRequest) {
				numStudentsForExam = ((NonClassRequest) e).getRosterList()
						.split(";").length;
			}
			expectedDuration *= numStudentsForExam - e.getAppointment().size();
			expectedDuration /= e.getTestRangeLength();
			expectedDurationSum += expectedDuration;
		}
		totalUTI = (double) currentDayUTI + expectedDurationSum
				/ (seatNum * openHourDuration);
		return totalUTI;
	}

	/**
	 * Calculate utilization for a given day including a request as if it was
	 * approved
	 * 
	 * @param d
	 *            the date for which to calculate the utilization
	 * @param request
	 *            the request to be included
	 * @return utilization for that day
	 */
	public double calculateUtilizationDayWithRequest(Date d, Request r) {
		String termID = dbManager.getTermByDate(d).getTermID();
		dateHandler = new ClosedDateHandler(termID);
		if (dateHandler.checkClosed(d)) {
			return -1;
		}
		double currentDayUTI = 0;
		int durationSum = 0;
		TestCenterInfo tci = dbManager.R_getTestCenterInfo(dbManager
				.getTermByDate(d).getTermID());
		List<Appointment> appList = dbManager.R_getAppointmentOnDate(d);
		int gapTime = tci.getGapTime();
		for (Appointment a : appList) {
			int appLength = a.getRequest().getTestDuration() + gapTime;
			// check if it needs to be rounded up
			if (appLength % 30 != 0) {
				appLength = ((appLength / 30) + 1) * 30;
			}
			durationSum += appLength;
		}
		int openHourDuration = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		Calendar c2 = Calendar.getInstance();
		int dayVal = c.get(Calendar.DAY_OF_WEEK);
		openHourDuration = OpenHoursParser.getHoursDifference(
				tci.getOpenHours(), dayVal);
		if (openHourDuration == -1) {
			return -1;
		}
		int seatNum = tci.getSeats();
		currentDayUTI = (double) durationSum / (seatNum * openHourDuration);
		// past or current day
		if (c.get(Calendar.YEAR) <= c2.get(Calendar.YEAR)
				&& c.get(Calendar.MONTH) <= c2.get(Calendar.MONTH)
				&& c.get(Calendar.DATE) <= c2.get(Calendar.DATE)) {
			return currentDayUTI;
		}
		// future day
		double totalUTI = currentDayUTI;
		int expectedDurationSum = 0;
		List<Request> exams = dbManager.getAllExamsByDate(d);
		// include the request in these calculations
		exams.add(r);
		for (Request e : exams) {
			int expectedDuration = e.getTestDuration() + gapTime;
			int numStudentsForExam = 0;
			if (e instanceof ClassExamRequest) {
				numStudentsForExam = dbManager
						.R_getStudentNum(((ClassExamRequest) e).getCourse());
			}
			if (e instanceof NonClassRequest) {
				numStudentsForExam = ((NonClassRequest) e).getRosterList()
						.split(";").length;
			}
			expectedDuration *= numStudentsForExam - e.getAppointment().size();
			expectedDuration /= e.getTestRangeLength();
			expectedDurationSum += expectedDuration;
		}
		totalUTI = (double) currentDayUTI + expectedDurationSum
				/ (seatNum * openHourDuration);
		return totalUTI;
	}
	
	/**
	 * Get the number of student taking the test from the request
	 * 
	 * @param request
	 *            Request for the test
	 * @return numbers of student taking the test
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
 ****TTTTTTTTTT*EEEEEEEEEE*******AAAA*******MM***********MM*5555555555
 ********TT*****EE**************AA**AA******MMM*********MMM*55********
 ********TT*****EE*************AA****AA*****MMMM*******MMMM*55*55555**
 ********TT*****EEEEEEEEEE****AAAAAAAAAA****MM*MM*****MM*MM*555****55*
 ********TT*****EE***********AA********AA***MM**MM***MM**MM*********55
 ********TT*****EE**********AA**********AA**MM***MM*MM***MM*55******55
 ********TT*****EEEEEEEEEE*AA************AA*MM****MMM****MM**55555555*
 ********************************************************************/
