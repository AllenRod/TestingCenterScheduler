package application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {

	public static final Logger myLogger = Logger.getLogger("Logger");

	private static LoggerWrapper instance = null;

	public static LoggerWrapper getInstance() {
		if (instance == null) {
			prepareLogger();
			instance = new LoggerWrapper();
		}
		return instance;
	}

	private static void prepareLogger() {
		FileHandler myFileHandler;
		try {
			myFileHandler = new FileHandler("C:/Log Files/log.txt");
			myFileHandler.setFormatter(new SimpleFormatter());
			myLogger.addHandler(myFileHandler);
			myLogger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
