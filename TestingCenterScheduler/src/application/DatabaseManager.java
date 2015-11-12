package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import entity.Appointment;
import entity.ClassExamRequest;
import entity.Course;
import entity.NonClassRequest;
import entity.Request;
import entity.Term;
import entity.TestCenterInfo;
import entity.UserAccount;

/**
 * Manage all database transaction and activity using EntityManagers.
 * 
 * @author CSE308 Team Five
 */
public class DatabaseManager {
	// entity manager factory object
	private EntityManagerFactory emf;

	// single entity manager object
	private EntityManager em;

	// singleton object of DatabaseManager
	private static DatabaseManager databasemanager = null;

	/**
	 * Constructor for class DatabaseManager
	 */
	public DatabaseManager() {
		emf = Persistence.createEntityManagerFactory("TestingCenterScheduler");
	}

	/**
	 * Get the user with the provided user name and password
	 * 
	 * @param userName
	 *            name of user
	 * @param pw
	 *            password of user
	 * @return UserAccount data
	 */
	public UserAccount getUser(String userName, String pw) {
		createEntityManager();

		Query q = em.createQuery("SELECT u FROM UserAccount u WHERE "
				+ "u.netID = :uid AND u.hashedPassword = :upw");
		q.setParameter("uid", userName);
		q.setParameter("upw", pw);
		UserAccount result = null;

		try {
			result = (UserAccount) q.getSingleResult();
			LoggerWrapper.logger.info("User " + result.getFirstName() + " "
					+ result.getLastName() + " log in as " + result.getRole());
			return result;
		} catch (Exception error) {
			LoggerWrapper.logger.warning("Error in finding user");
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Get the term by the given term id
	 * 
	 * @param termID
	 *            Given term id
	 * @return Term with the given term id
	 */
	public Term getTermByID(String termID) {
		createEntityManager();
		Query q = em.createQuery("SELECT t FROM Term t WHERE t.termID = :tid");
		q.setParameter("tid", termID);
		Term term = null;
		try {
			term = (Term) q.getSingleResult();
			LoggerWrapper.logger.info("Term " + term.getTerm() + " is found");
			return term;
		} catch (Exception error) {
			LoggerWrapper.logger.warning("Error in finding term by id");
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Get the term by the given date
	 * 
	 * @param date
	 *            Given date
	 * @return Term where the given date belong
	 */
	public Term getTermByDate(Date date) {
		createEntityManager();
		Query q = em
				.createQuery("SELECT t FROM Term t WHERE t.startDate <= :td AND t.endDate >= :td");
		q.setParameter("td", date, TemporalType.DATE);
		Term term = null;
		try {
			term = (Term) q.getSingleResult();
			LoggerWrapper.logger.info("Term " + term.getTerm() + " is found");
			return term;
		} catch (Exception error) {
			LoggerWrapper.logger.warning("Error in finding term by date");
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Delete table by given table name and term ID
	 * 
	 * @param tableName
	 *            table of data to be deleted
	 * @return if the transaction is successful
	 */
	public boolean delTable(String tableName, String termID) {
		try {
			Term term = this.getTermByID(termID);
			createTransactionalEntityManager();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
			em.createQuery(
					"DELETE FROM " + tableName
							+ " tab WHERE tab.term = :termDel")
					.setParameter("termDel", term).executeUpdate();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
			LoggerWrapper.logger.info("Table " + tableName
					+ " with data in term " + termID + " is deleted");
			closeTransactionalEntityManager();
			return true;
		} catch (Exception error) {
			LoggerWrapper.logger.warning("Error in deleting in table "
					+ tableName + "\n" + error.getClass() + ":"
					+ error.getMessage());
			closeEntityManager();
			return false;
		}
	}

	/**
	 * Import a single entity into database
	 * 
	 * @param data
	 *            single entity to be persisted into database
	 * @return if the data is successfully imported into database
	 */
	public String loadData(Object data) {
		createTransactionalEntityManager();
		try {
			em.persist(data);
			closeTransactionalEntityManager();
			LoggerWrapper.logger.info("Data imported into database");
			return "Data import succeeds";
		} catch (PersistenceException error) {
			closeEntityManager();
			LoggerWrapper.logger.warning("Data import error occured:\n"
					+ error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}

	/**
	 * Load a chunk of data(entities) in ArrayLit into database
	 * 
	 * @param dataList
	 *            ArrayList that contains the data
	 * @return if all data are successfully imported into database
	 */
	public String loadDataList(ArrayList<Object> dataList) {
		createTransactionalEntityManager();
		try {
			Iterator<Object> it = dataList.iterator();
			int c = 0;
			int n = 0;
			while (it.hasNext()) {
				Object e = it.next();
				em.persist(e);
				c++;
				n++;
				if (c >= 500) {
					em.flush();
					LoggerWrapper.logger.info("Flush entity manager");
					c = 0;
				}
			}
			closeTransactionalEntityManager();
			LoggerWrapper.logger.info("All data successfully imported. "
					+ "Total of " + n + " rows inserted into database");
			return "All data imports succeed";
		} catch (PersistenceException error) {
			closeEntityManager();
			LoggerWrapper.logger.warning("Data import error occured:\n"
					+ error.getClass() + ":" + error.getMessage());
			return error.getMessage();
		}
	}

	/**
	 * Update Request entity
	 * 
	 * @param RID
	 *            Given request ID
	 * @param type
	 *            Type of request to be updated
	 * @param examName
	 *            Updated exam name
	 * @param testDuration
	 *            Updated exam duration
	 * @param startTime
	 *            Updated exam start time
	 * @param endTime
	 *            Updated exam end time
	 * @param roster
	 *            Updated roster list, for non class exam only
	 * @return if the entity is updated
	 */
	public String I_editRequest(String RID, String type, String examName,
			String testDuration, Date startTime, Date endTime, String roster) {
		createTransactionalEntityManager();
		try {
			if (type.equals("CLASS")) {
				Query q = em
						.createQuery("UPDATE ClassExamRequest SET examName = :name, "
								+ "testDuration = :dur, timeStart = :times, "
								+ "timeEnd = :timee WHERE examIndex = :id");
				q.setParameter("name", examName);
				q.setParameter("dur", Integer.parseInt(testDuration));
				q.setParameter("times", startTime, TemporalType.TIMESTAMP);
				q.setParameter("timee", endTime, TemporalType.TIMESTAMP);
				q.setParameter("id", Integer.parseInt(RID));
				q.executeUpdate();
			} else if (type.equals("AD_HOC")) {
				Query q = em
						.createQuery("UPDATE NonClassRequest SET examName = :name, "
								+ "testDuration = :dur, timeStart = :times, "
								+ "timeEnd = :timee, rosterList = :rlist WHERE examIndex = :id");
				q.setParameter("name", examName);
				q.setParameter("dur", Integer.parseInt(testDuration));
				q.setParameter("times", startTime, TemporalType.TIMESTAMP);
				q.setParameter("timee", endTime, TemporalType.TIMESTAMP);
				q.setParameter("rlist", roster);
				q.setParameter("id", Integer.parseInt(RID));
				q.executeUpdate();
			}
			closeTransactionalEntityManager();
			return "Update succeed";
		} catch (Exception error) {
			closeEntityManager();
			LoggerWrapper.logger.warning("Error in I_editRequest:"
					+ error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}

	/**
	 * Delete Request entity
	 * 
	 * @param RID
	 *            Given request ID
	 * @return if the entity is deleted
	 */
	public String I_deleteRequest(String RID) {
		createTransactionalEntityManager();
		try {
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
			em.createQuery("DELETE FROM Request r WHERE r.examIndex = :rid")
					.setParameter("rid", Integer.parseInt(RID)).executeUpdate();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
			closeTransactionalEntityManager();
			return "Delete succeed";
		} catch (Exception error) {
			closeEntityManager();
			LoggerWrapper.logger.warning("Error in I_deleteRequest:"
					+ error.getClass() + ":" + error.getMessage());
			return error.getClass() + ":" + error.getMessage();
		}
	}

	/**
	 * Find the course by the given courseID, instructor netID and term
	 * 
	 * @param courseID
	 *            Given courseID
	 * @param term
	 *            Given term
	 * @return Course with the courseID and term
	 */
	public Course I_findCourse(String courseID, Term term) {
		createEntityManager();
		Query q = em
				.createQuery("SELECT c FROM Course c WHERE c.classID = :cID "
						+ "AND c.term = :cTerm");
		q.setParameter("cID", courseID);
		q.setParameter("cTerm", term);
		try {
			Course rs = (Course) q.getSingleResult();
			if (rs == null) {
				LoggerWrapper.logger
						.info("Course not found, conflict between courseID and termID");
				return null;
			}
			LoggerWrapper.logger.info("Get course with course " + courseID
					+ ", instruction netID " + rs.getInstructorNetID()
					+ " and termID " + term.getTermID());
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in I_findCourse:\n"
					+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Queries DB by InstructorNetID and returns a list of courses
	 * 
	 * @param String
	 *            Name of instructor to get courses for
	 * @return List<Course> List of all courses belonging to the instructor
	 */
	public List<Course> I_getCourses(String netID) {
		createEntityManager();
		Query a = em
				.createQuery("SELECT c FROM Course c WHERE c.instructorNetID = :nID");
		a.setParameter("nID", netID);
		try {
			List<Course> rs = a.getResultList();
			LoggerWrapper.logger.info("Get courses belongs to " + netID);
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in I_getCourses:\n"
					+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Queries DB by InstructorNetID and returns a list of ClassExamRequests
	 * 
	 * @param String
	 *            Name of instructor to get requests for
	 * @return List<Course> List of all class exam requests belonging to the
	 *         instructor
	 */
	public List<Request> I_getClassExamRequests(String netID) {
		createEntityManager();
		Query a = em
				.createQuery("SELECT r FROM ClassExamRequest r WHERE r.instructorNetID = :nID ORDER BY r.examIndex DESC");
		a.setParameter("nID", netID);
		try {
			List<Request> rs = a.getResultList();
			LoggerWrapper.logger.info("Get class exam requests belongs to "
					+ netID);
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger
					.info("There is an error in I_getClassExamRequests:\n"
							+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Queries DB by InstructorNetID and returns a list of NonClassRequests
	 * 
	 * @param String
	 *            Name of instructor to get requests for
	 * @return List<Course> List of all non-class requests belonging to the
	 *         instructor
	 */
	public List<Request> I_getNonClassRequests(String netID) {
		createEntityManager();
		Query a = em
				.createQuery("SELECT r FROM NonClassRequest r WHERE r.instructorNetID = :nID ORDER BY r.examIndex DESC");
		a.setParameter("nID", netID);
		try {
			List<Request> rs = a.getResultList();
			LoggerWrapper.logger.info("Get non-class exam requests belongs to "
					+ netID);
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger
					.info("There is an error in I_getNonClassRequests:\n"
							+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Set class exam name based on the given class ID
	 * 
	 * @param String
	 *            Given class ID
	 * @return String Appropriate class exam name
	 */
	public String I_setClassExamName(String classID) {
		createEntityManager();
		Query a = em
				.createQuery("SELECT c FROM Course c WHERE c.classID = :cID");
		a.setParameter("cID", classID);
		try {
			Course course = (Course) a.getSingleResult();
			a = em.createQuery("SELECT COUNT(r.examIndex) FROM ClassExamRequest r WHERE r.course = :c");
			a.setParameter("c", course);
			long num = (long) a.getSingleResult();
			num += 1;
			String rs = course.getSubject() + course.getCatalogNum() + "-"
					+ course.getSection() + "_" + course.getTerm().getTermID()
					+ "_ex" + num;
			LoggerWrapper.logger.info("The exam name would be " + rs);
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger
					.info("There is an error in I_getNonClassRequests:\n"
							+ error.getClass() + ":" + error.getMessage());
			return "";
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Queries DB and returns a list of TestCenterInfo by term
	 * 
	 * @return List<TestCenterInfo> List of all test center info by term
	 */
	public List<TestCenterInfo> A_getTCInfo() {
		createEntityManager();
		Query a = em.createQuery("SELECT t FROM TestCenterInfo t");
		try {
			List<TestCenterInfo> rs = a.getResultList();
			LoggerWrapper.logger.info("Get all existing testing center info");
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in A_getTCInfo:\n"
					+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Calculates the utilization for a given day
	 * 
	 * @param t
	 *            the term that day is in
	 * @param d
	 *            the date to find the utilization of
	 * @return the utilization of the given day
	 */
	public double calculateUtilization(String t, Date d) {
		createEntityManager();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, 1);
		Date d2 = c.getTime();
		Query q1 = em
				.createQuery("SELECT a FROM Appointment a WHERE :d <= a.timeStart AND  a.timeStart <= :d2");
		q1.setParameter("d", d, TemporalType.DATE);
		q1.setParameter("d2", d2, TemporalType.DATE);
		List<Appointment> appList = q1.getResultList();
		int durationSum = 0;
		TestCenterInfo tci = em.find(TestCenterInfo.class, t);
		for (Appointment a : appList) {
			durationSum += a.getRequest().getTestDuration() + tci.getGapTime();
		}
		int openHourDuration = 0;
		c.setTime(d);
		int dayVal = c.get(Calendar.DAY_OF_WEEK);
		openHourDuration = OpenHoursParser.getHoursDifference(
				tci.getOpenHours(), dayVal);
		if (openHourDuration == -1) {
			return -1;
		}
		int seatNum = tci.getSeats();
		closeEntityManager();
		return (double) durationSum / (seatNum * openHourDuration);

	}

	/**
	 * Queries DB and returns a list of Term
	 * 
	 * @return List<Term> List of all term
	 */
	public List<Term> getTerm() {
		createEntityManager();
		Query a = em.createQuery("SELECT t FROM Term t");
		try {
			List<Term> rs = a.getResultList();
			LoggerWrapper.logger.info("Get all existing term");
			return rs;
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in A_getTerm:\n"
					+ error.getClass() + ":" + error.getMessage());
			return null;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Check if the test center info for a term already existed
	 * 
	 * @param term
	 *            The term to check
	 */
	public void A_checkTerm(String term) {
		createTransactionalEntityManager();
		try {
			TestCenterInfo t = em.find(TestCenterInfo.class, term);
			if (t == null) {
				LoggerWrapper.logger.info("Term " + term + " does not exist");
				return;
			} else {
				LoggerWrapper.logger.info("Term " + term
						+ " already exists, info would be updated");
				em.remove(t);
			}
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in A_checkTerm:\n"
					+ error.getClass() + ":" + error.getMessage());
		} finally {
			closeTransactionalEntityManager();
		}
	}

	/**
	 * Get the number of student from Roster with the given course
	 * 
	 * @param course
	 *            Given course
	 * @return Number of student in the course
	 */
	public int R_getStudentNum(Course course) {
		createEntityManager();
		try {
			Query q = em
					.createQuery("SELECT COUNT(r.user) FROM Roster r WHERE r.course = :cou "
							+ " AND r.term = :cterm");
			q.setParameter("cou", course.getClassID());
			q.setParameter("cterm", course.getTerm());
			Long r = (Long) q.getSingleResult();
			return r.intValue();
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in R_getStudentNum:\n"
					+ error.getClass() + ":" + error.getMessage());
			return 0;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Get the gap time from TestCenterInfo with the given term
	 * 
	 * @param term
	 *            Given term
	 * @return Gap time of the testing center in the given term
	 */
	public int R_getGapTime(Term term) {
		createEntityManager();
		try {
			Query q = em
					.createQuery("SELECT t.gapTime FROM TestCenterInfo t WHERE t.term = :tterm");
			q.setParameter("tterm", term);
			int r = (int) q.getSingleResult();
			return r;
		} catch (PersistenceException error) {
			LoggerWrapper.logger.info("There is an error in R_getGapTime:\n"
					+ error.getClass() + ":" + error.getMessage());
			return 0;
		} finally {
			closeEntityManager();
		}
	}

	/**
	 * Returns a list of request based on the type
	 * 
	 * @param type
	 *            either CLASS or AD HOC
	 * @return list of requests with given type
	 */
	public List<Request> getRequests(String type) {
		createEntityManager();
		Query q1 = em.createQuery("SELECT r FROM Request r");
		List<Request> rList = q1.getResultList();
		List<Request> returnList = new ArrayList<Request>();
		for (Request r : rList) {
			if (type.equals("CLASS") && r instanceof ClassExamRequest) {
				returnList.add(r);
			} else if (type.equals("AD HOC") && r instanceof NonClassRequest) {
				returnList.add(r);
			}
		}
		closeEntityManager();
		return returnList;
	}

	public List<Appointment> getAllAppointments() {
		createEntityManager();
		Query q1 = em.createQuery("SELECT a FROM Appointment a");
		List<Appointment> aList = q1.getResultList();
		closeEntityManager();
		return aList;
	}

	/**
	 * Return a singleton of DatabaseManager
	 * 
	 * @return a singleton of class DatabaseManager
	 */
	public static DatabaseManager getSingleton() {
		if (databasemanager == null) {
			databasemanager = new DatabaseManager();
		}
		return databasemanager;
	}

	/**
	 * Start entity transaction
	 */
	private void createTransactionalEntityManager() {
		// Create a new EntityManager
		em = emf.createEntityManager();
		// Begin transaction
		em.getTransaction().begin();
		LoggerWrapper.logger.info("Transaction begins");
	}

	/**
	 * Commit transaction and close EntityManager
	 */
	private void closeTransactionalEntityManager() {
		// Commit the transaction
		em.getTransaction().commit();
		// Close this EntityManager
		em.close();
		LoggerWrapper.logger.info("Transaction commits");
	}

	/**
	 * Create new EntityManager
	 */
	private void createEntityManager() {
		// Create a new EntityManager
		em = emf.createEntityManager();
		LoggerWrapper.logger.info("Create entity manager");
	}

	/**
	 * Close the constructed EntityManager
	 */
	private void closeEntityManager() {
		// Close this EntityManager
		em.close();
		LoggerWrapper.logger.info("Close entity manager");
	}
}
