package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.Course;
import entity.Roster;
import entity.User;
import entity.UserAccount;

public class DatabaseManager {
	private EntityManagerFactory emf;

	private EntityManager em;

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

	public void loadCSV(String fileName, String table) {
		if (!(table.toLowerCase().equals("user")
				|| table.toLowerCase().equals("class") || table.toLowerCase()
				.equals("roster"))) {
			System.out.println("You did not enter a correct table name");
			return;
		}
		createEntityManager();
		EntityTransaction trans = em.getTransaction();
		TypedQuery<Long> q = null;
		if (!fileName.endsWith(".csv")) {
			System.out.println("File must be .csv file");
			return;
		}
		File file = new File(fileName);
		Scanner sc;
		try {
			sc = new Scanner(file);
			sc.nextLine(); // ignore header line
			while (sc.hasNext()) {
				String line = sc.nextLine();
				// Guess what? A comma separates in a comma separated values
				// file
				String[] values = line.split(",");
				if (table.toLowerCase().equals("user")) {
					// Check if user is already in table
					q = em.createQuery(
							"SELECT COUNT(u) FROM User u WHERE u.netID = :uid",
							Long.class);
					q.setParameter("uid", values[2]);
					Long i = q.getSingleResult();
					if (i.intValue() == 0) {
						User user = new User();
						user.setFirstName(values[0]);
						user.setLastName(values[1]);
						user.setNetID(values[2]);
						user.setEmail(values[3]);
						trans.begin();
						em.persist(user);
						trans.commit();
					} else {
						System.out.println("User is already in the table");
					}
				} else if (table.toLowerCase().equals("class")) {
					q = em.createQuery(
							"SELECT COUNT(c) FROM Course c WHERE c.classID = :cid",
							Long.class);
					q.setParameter("cid", values[0]);
					Long i = q.getSingleResult();
					if (i.intValue() == 0) {
						Course c = new Course();
						c.setClassID(values[0]);
						c.setSubject(values[1]);
						c.setCatalogNum(Integer.parseInt(values[2]));
						c.setSection(values[3]);
						c.setInstructorNetID(values[4]);
						trans.begin();
						em.persist(c);
						trans.commit();
					} else {
						System.out.println("Course is already in table");
					}
				} else if (table.toLowerCase().equals("roster")) {
					q = em.createQuery(
							"SELECT COUNT(c) FROM Course c WHERE c.classID = :cid",
							Long.class);
					q.setParameter("cid", values[1]);
					Long i = q.getSingleResult();
					// if the course exists
					if (i.intValue() == 1) {
						// if the user is not already in the table
						q = em.createQuery(
								"SELECT COUNT(r) FROM Roster r WHERE r.user = :rid",
								Long.class);
						q.setParameter("rid", values[0]);
						if (q.getSingleResult().intValue() == 0) {
							Course c = em.find(Course.class, values[1]);
							Roster r = new Roster();
							r.setUser(values[0]);
							r.setCourse(c);
							trans.begin();
							em.persist(r);
							trans.commit();
						} else {
							System.out.println("Roster info already in table");
						}

					} else {
						System.out.println("Error, course does not exist.");
					}

				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the file");
		}

		closeEntityManager();

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
