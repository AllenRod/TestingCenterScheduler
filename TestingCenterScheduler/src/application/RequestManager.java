package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.Appointment;
import entity.ClassExamRequest;
import entity.NonClassRequest;
import entity.Request;
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
		// Get all request end before this request ends
		List<Request> beforeList = dbManager.R_getRequestWithPos(
				request.getTimeStart(), request.getTimeEnd(), -1);
		// Get all request start before this request starts
		// and end after this request ends
		List<Request> betweenList = dbManager.R_getRequestWithPos(
				request.getTimeStart(), request.getTimeEnd(), 0);
		// Get all request end after this request ends
		List<Request> afterList = dbManager.R_getRequestWithPos(
				request.getTimeStart(), request.getTimeEnd(), 1);
		// Get all request overlapped over the given request
		List<Request> overList = dbManager.R_getRequestWithPos(
				request.getTimeStart(), request.getTimeEnd(), 2);
		Calendar overEarly = Calendar.getInstance();
		Calendar overLate = Calendar.getInstance();
		if (overList != null) {
			overEarly.setTime(overList.get(0).getTimeStart());
			overLate.setTime(overList.get(0).getTimeEnd());
			Calendar ctemp = Calendar.getInstance();
			for (Request r : overList) {
				ctemp.setTime(r.getTimeStart());
				if (ctemp.before(overEarly)) {
					overEarly = (Calendar) ctemp.clone();
				}
				ctemp.setTime(r.getTimeEnd());
				if (ctemp.after(overLate)) {
					overLate = (Calendar) ctemp.clone();
				}
			}
		}
		// Get the earliest start time, taking account of the request date
		Calendar cS = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		if ((beforeList != null) && (overList != null)) {
			cS.setTime(beforeList.get(0).getTimeStart());
			c = (Calendar) overEarly.clone();
			if (cS.after(c)) {
				cS = (Calendar) c.clone();
			}
		} else {
			if ((beforeList == null) && (overList == null)) {
				cS.setTime(request.getTimeStart());
			} else if (beforeList == null) {
				cS = (Calendar) overEarly.clone();
			} else if (overList == null) {
				cS.setTime(beforeList.get(0).getTimeStart());
			}
		}
		c.setTime(request.getRequestDate());
		if (cS.before(c)) {
			cS = (Calendar) c.clone();
		}
		// Get the latest end time
		Calendar cE = Calendar.getInstance();
		if ((afterList != null) && (overList != null)) {
			cE.setTime(afterList.get(afterList.size() - 1).getTimeEnd());
			c = (Calendar) overLate.clone();
			if (cE.before(c)) {
				cE = (Calendar) c.clone();
			}
		} else {
			if ((afterList == null) && (overList == null)) {
				cE.setTime(request.getTimeEnd());
			} else if (afterList == null) {
				cE = (Calendar) overLate.clone();
			} else if (overList == null) {
				cE.setTime(afterList.get(afterList.size() - 1).getTimeEnd());
			}
		}
		// Generate list of TimeSlotHandler
		List<TimeSlotHandler> slotList = new ArrayList<>();
		cS.set(Calendar.HOUR, 0);
		cS.set(Calendar.MINUTE, 0);
		cS.set(Calendar.SECOND, 0);
		do {
			slotList.add(new TimeSlotHandler(cS.getTime()));
			cS.add(Calendar.DATE, 1);
		} while (!cS.after(cE));
		boolean schedulable = true;
		// Fill appointments for beforeList
		schedulable = this.fillInAppointment(slotList, beforeList);
		// Fill appointments for current request
		List<Request> current = new ArrayList<>();
		current.add(request);
		schedulable = this.fillInAppointment(slotList, current);
		if (!schedulable) {
			return false;
		}
		// Fill appointments for betweenList
		schedulable = this.fillInAppointment(slotList, betweenList);
		if (!schedulable) {
			return false;
		}
		// Fill appointments for afterList
		schedulable = this.fillInAppointment(slotList, afterList);
		if (!schedulable) {
			return false;
		}
		// Fill appointments for overList
		schedulable = this.fillInAppointment(slotList, overList);
		if (!schedulable) {
			return false;
		}
		return true;
	}

	/**
	 * Insert unmade appointments for the requests in the given request list to
	 * a given timeslot list
	 * 
	 * @param slotList
	 *            Given timeslot list
	 * @param rList
	 *            Given request list
	 * @return True if all unmade appointments can be inserted into the given
	 *         timeslot list
	 */
	private boolean fillInAppointment(List<TimeSlotHandler> slotList,
			List<Request> rList) {
		// Iterate through every request to fill in potential appointments
		for (Request r : rList) {
			// Get number of unmade appointments
			int reqNum = getStudentNum(r) - dbManager.R_getAppointmentNum(r);
			// Get start time and end time
			Calendar cStart = Calendar.getInstance();
			cStart.setTime(r.getTimeStart());
			Calendar cEnd = Calendar.getInstance();
			cEnd.setTime(r.getTimeEnd());
			// Find the starting date in slotList
			int index = 0;
			for (index = 0; index < slotList.size(); index++) {
				if (slotList.get(index).checkDate(cStart.getTime())) {
					break;
				}
			}
			// Insert appointments through each day
			while (!cStart.after(cEnd)) {
				TimeSlotHandler handler = slotList.get(index);
				reqNum = handler.checkInsertApp(r, reqNum);
				cStart.add(Calendar.DATE, 1);
				index++;
				if (reqNum == 0) {
					break;
				}
			}
			// If there are still appointments need to be inserted but there is
			// no more slot, return false
			if (reqNum > 0) {
				return false;
			}
		}
		return true;
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
		ClosedDateHandler dateHandler = new ClosedDateHandler(termID);
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
		ClosedDateHandler dateHandler = new ClosedDateHandler(termID);
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
 **** TTTTTTTTTT*EEEEEEEEEE*******AAAA*******MM***********MM*5555555555
 * TT*****EE**************AA**AA******MMM*********MMM*55********
 * TT*****EE*************AA****AA*****MMMM*******MMMM*55*55555**
 * TT*****EEEEEEEEEE****AAAAAAAAAA****MM*MM*****MM*MM*555****55*
 * TT*****EE***********AA********AA***MM**MM***MM**MM*********55
 * TT*****EE**********AA**********AA**MM***MM*MM***MM*55******55
 * TT*****EEEEEEEEEE*AA************AA*MM****MMM****MM**55555555*
 ********************************************************************/
