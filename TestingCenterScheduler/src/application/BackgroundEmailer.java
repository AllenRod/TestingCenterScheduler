package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class that handles sending emails about appointments
 * 
 *
 */
@WebListener
public class BackgroundEmailer implements ServletContextListener {

	private ScheduledExecutorService scheduler;

	public BackgroundEmailer() {
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		scheduler.shutdownNow();

	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new EmailReminder(), 0, 30,
				TimeUnit.SECONDS);
	}

}
