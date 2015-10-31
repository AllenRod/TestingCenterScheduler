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
import entity.User;
import entity.UserAccount;

/**
 * Manage all database transaction and activity using EntityManagers.
 * 
 * @author CSE308 Team Five
 */
public class DatabaseManager {
    private EntityManagerFactory emf;

    private EntityManager em;

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
	    return result;
	} catch (Exception NoResultException) {
	    return null;
	} finally {
	    closeEntityManager();
	}

    }

    public boolean emptyTable(String tableName) {
	try {
	    createTransactionalEntityManager();
	    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
	    em.createNativeQuery("TRUNCATE " + tableName).executeUpdate();
	    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
	    closeTransactionalEntityManager();
	    return true;
	} catch (Exception error) {
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
	    return "Data import succeeds";
	} catch (PersistenceException error) {
	    closeEntityManager();
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
	    while (it.hasNext()) {
		Object e = it.next();
		em.persist(e);
		c++;
		if (c >= 500) {
		    em.flush();
		    System.out.println("flushed");
		    c = 0;
		}
	    }
	    closeTransactionalEntityManager();
	    return "All data imports succeed";
	} catch (PersistenceException error) {
	    closeEntityManager();
	    return error.getMessage();
	}
    }

    /**
     * Start entity transaction
     */
    private void createTransactionalEntityManager() {
	// Create a new EntityManager
	em = emf.createEntityManager();
	// Begin transaction
	em.getTransaction().begin();
    }

    /**
     * Commit transaction and close EntityManager
     */
    private void closeTransactionalEntityManager() {
	// Commit the transaction
	em.getTransaction().commit();
	// Close this EntityManager
	em.close();
    }

    /**
     * Create new EntityManager
     */
    private void createEntityManager() {
	// Create a new EntityManager
	em = emf.createEntityManager();
    }

    /**
     * Close the constructed EntityManager
     */
    private void closeEntityManager() {
	// Close this EntityManager
	em.close();
    }

    /**
     * Kevin document this
     * 
     * @return
     */
    public List<Course> I_getCourses() {
	createEntityManager();
	Query a = em.createQuery("SELECT c FROM Course c");
	try {
	    List<Course> rs = a.setMaxResults(10).getResultList();
	    return rs;
	} catch (Exception NoResultException) {
	    return null;
	} finally {
	    closeEntityManager();
	}
    }

    enum RequestStatus {
	PENDING, APPROVED, DENIED, COMPLETED
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
}
