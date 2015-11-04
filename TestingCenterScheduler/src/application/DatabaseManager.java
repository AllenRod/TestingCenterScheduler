package application;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import entity.Course;
import entity.Request;
import entity.Roster;
import entity.TestCenterInfo;
import entity.User;
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

    // logger wrapper object
    private LoggerWrapper wrapper;

    /**
     * Constructor for class DatabaseManager
     */
    public DatabaseManager() {
	emf = Persistence.createEntityManagerFactory("TestingCenterScheduler");
	wrapper = LoggerWrapper.getInstance();
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
	    wrapper.logger.info("User " + result.getFirstName() + " "
		    + result.getLastName() + " log in as " + result.getRole());
	    return result;
	} catch (Exception error) {
	    wrapper.logger.warning("Error in user login");
	    return null;
	} finally {
	    closeEntityManager();
	}
    }
    
    /**
     * Empty table by given table name
     * @param tableName		table to be emptied
     * @return			if the transaction is successful
     */
    public boolean emptyTable(String tableName) {
	try {
	    createTransactionalEntityManager();
	    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
	    em.createNativeQuery("TRUNCATE " + tableName).executeUpdate();
	    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
	    wrapper.logger.info("Table " + tableName + " is emptied");
	    closeTransactionalEntityManager();
	    return true;
	} catch (Exception error) {
	    wrapper.logger.warning("Error in emptying table " + tableName);
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
	    wrapper.logger.info("Data imported into database");
	    return "Data import succeeds";
	} catch (PersistenceException error) {
	    closeEntityManager();
	    wrapper.logger.warning("Data import error occured:\n"
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
		    wrapper.logger.info("Flush entity manager");
		    c = 0;
		}
	    }
	    closeTransactionalEntityManager();
	    wrapper.logger.info("All data successfully imported. "
		    + "Total of " + n + " rows inserted into database");
	    return "All data imports succeed";
	} catch (PersistenceException error) {
	    closeEntityManager();
	    wrapper.logger.warning("Data import error occured:\n"
		    + error.getClass() + ":" + error.getMessage());
	    return error.getMessage();
	}
    }

    /**
     * Find the course by the given courseID and instructor netID
     * @param courseID		Given courseID
     * @param netID		Given instructor netID
     * @return			Course with the courseID and instructor netID
     */
    public Course I_findCourse(String courseID, String netID) {
	createEntityManager();
	Query q = em
		.createQuery("SELECT c FROM Course c WHERE c.classID = :cID "
			+ "AND c.instructorNetID = :nID");
	q.setParameter("cID", courseID);
	q.setParameter("nID", netID);
	try {
	    Course rs = (Course) q.getSingleResult();
	    wrapper.logger.info("Get course with course " + courseID + " and instruction netID "
		    + netID);
	    return rs;
	} catch (PersistenceException error) {
	    wrapper.logger.info("There is an error in I_findCourse:\n" + error.getClass() 
		    + ":" + error.getMessage());
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
	    wrapper.logger.info("Get courses belongs to " + netID);
	    return rs;
	} catch (PersistenceException error) {
	    wrapper.logger.info("There is an error in I_getCourses:\n" + error.getClass() 
		    + ":" + error.getMessage());
	    return null;
	} finally {
	    closeEntityManager();
	}
    }

    /**
     * Queries DB by InstructorNetID and returns a list of Requests
     * 
     * @param String
     *            Name of instructor to get requests for
     * @return List<Course> List of all requests belonging to the instructor
     */
    public List<Request> I_getRequests(String netID) {
	createEntityManager();
	Query a = em
		.createQuery("SELECT r FROM Request r WHERE r.instructorNetID = :nID");
	a.setParameter("nID", netID);
	try {
	    List<Request> rs = a.getResultList();
	    wrapper.logger.info("Get requests belongs to " + netID);
	    return rs;
	} catch (PersistenceException error) {
	    wrapper.logger.info("There is an error in I_getRequests:\n" + error.getClass() 
		    + ":" + error.getMessage());
	    return null;
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
	    wrapper.logger.info("Get all existing testing center info");
	    return rs;
	} catch (PersistenceException error) {
	    wrapper.logger.info("There is an error in A_getTCInfo:\n" + error.getClass() 
		    + ":" + error.getMessage());
	    return null;
	} finally {
	    closeEntityManager();
	}
    }

    public void A_checkTerm(String term) {
	createTransactionalEntityManager();
	try {
	    TestCenterInfo t = em.find(TestCenterInfo.class, term);
	    System.out.println("Got t");
	    if (t == null) {
		wrapper.logger.info("Term " + term + " does not exist");
		return;
	    } else {
		wrapper.logger.info("Term " + term + " already exists, info would be updated");
		em.remove(t);
	    }
	} catch (PersistenceException error) {
	    wrapper.logger.info("There is an error in A_checkTerm:\n" + error.getClass() 
		    + ":" + error.getMessage());
	} finally {
	    closeTransactionalEntityManager();
	}
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
	wrapper.logger.info("Transaction begins");
    }

    /**
     * Commit transaction and close EntityManager
     */
    private void closeTransactionalEntityManager() {
	// Commit the transaction
	em.getTransaction().commit();
	// Close this EntityManager
	em.close();
	wrapper.logger.info("Transaction commits");
    }

    /**
     * Create new EntityManager
     */
    private void createEntityManager() {
	// Create a new EntityManager
	em = emf.createEntityManager();
	wrapper.logger.info("Create entity manager");
    }

    /**
     * Close the constructed EntityManager
     */
    private void closeEntityManager() {
	// Close this EntityManager
	em.close();
	wrapper.logger.info("Close entity manager");
    }
}
