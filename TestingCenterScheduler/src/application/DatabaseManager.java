package application;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.UserAccount;

public class DatabaseManager {
    private EntityManagerFactory emf;

    private EntityManager em;

    public DatabaseManager() {
	emf = Persistence.createEntityManagerFactory("TestingCenterScheduler");
    }

    public String getRole(String userName, String pw) {
	createTransactionalEntityManager();
	Query q = em.createQuery("SELECT u FROM UserAccount u WHERE "
		+ "u.netID = :uid AND u.hashedPassword = :upw");
	q.setParameter("uid", userName);
	q.setParameter("upw", pw);
	try {
	    UserAccount result = (UserAccount) q.getSingleResult();
	    return result.getRoles();
	} catch (Exception NoResultException) {
	    return "";
	}
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
}
