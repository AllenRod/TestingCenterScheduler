package servlets;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import application.EmailReminder;
import application.LoggerWrapper;

/**
 * Class that handles sending emails about appointments
 * 
 * @author CSE308 Team Five
 */
@WebListener
public class BackgroundEmailer implements ServletContextListener {

	// ScheduledExecutorService object
	private ScheduledExecutorService scheduler;

	public BackgroundEmailer() {
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		LoggerWrapper.logger.info("Background Emailer starts");
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new EmailReminder(), 0, 10,
				TimeUnit.MINUTES);
	}
}
