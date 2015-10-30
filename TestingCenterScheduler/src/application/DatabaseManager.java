package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.Course;
import entity.Request;
import entity.Roster;
import entity.User;
import entity.UserAccount;

public class DatabaseManager {
    private EntityManagerFactory emf;

    private EntityManager em;

    private static DatabaseManager databasemanager = null;

    public DatabaseManager() {
	emf = Persistence.createEntityManagerFactory("TestingCenterScheduler");
    }

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

    public void loadData(ArrayList<Object> dataList) {
	System.out.println("start loading");
	createTransactionalEntityManager();
	Iterator<Object> it = dataList.iterator();
	int c = 0;
	while (it.hasNext()) {
	    Object e = it.next();
	    em.persist(e);
	    c++;
	    if (c >= 1000) {
		em.flush();
	    }
	}
	closeTransactionalEntityManager();
    }

    private void createTransactionalEntityManager() {
	// Create a new EntityManager
	em = emf.createEntityManager();
	// Begin transaction
	em.getTransaction().begin();
    }

    private void closeTransactionalEntityManager() {
	// Commit the transaction
	em.getTransaction().commit();
	// Close this EntityManager
	em.close();
    }

    private void createEntityManager() {
	// Create a new EntityManager
	em = emf.createEntityManager();
    }

    private void closeEntityManager() {
	// Close this EntityManager
	em.close();
    }

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

    public static DatabaseManager getSingleton() {
	if (databasemanager == null) {
	    databasemanager = new DatabaseManager();
	}
	return databasemanager;
    }
}
