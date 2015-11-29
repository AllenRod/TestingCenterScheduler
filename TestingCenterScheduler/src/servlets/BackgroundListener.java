package servlets;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import application.DatabaseManager;
import application.EmailReminder;
import application.LoggerWrapper;

/**
 * Class that handles sending emails about appointments
 * 
 * @author CSE308 Team Five
 */
@WebListener
public class BackgroundListener implements ServletContextListener {

	// ScheduledExecutorService object
	private ScheduledExecutorService scheduler;
	
	// DatabaseManager singleton object
	private DatabaseManager dbManager;
	
	public BackgroundListener() {
		super();
		dbManager = DatabaseManager.getSingleton();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
		dbManager.closeEntityManager();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		dbManager.createEntityManager();
		LoggerWrapper.logger.info("Background Emailer starts");
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new EmailReminder(), 0, 15,
				TimeUnit.MINUTES);
	}
}
