package application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {

	public static final Logger logger = Logger.getLogger("Logger");

	private static LoggerWrapper instance = null;

	public static LoggerWrapper getInstance() {
		if (instance == null) {
			prepareLogger();
			instance = new LoggerWrapper();
		}
		return instance;
	}

	private static void prepareLogger() {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("/application.log");
			System.out.println(fileHandler.toString());
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
