package application;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Appointment;

/**
 * Class that sends email to student
 *
 * @author CSE308 Team Five
 */
public class EmailReminder implements Runnable {

	// set of appointments students need to be emailed about
	Set<Appointment> appSet;

	// single DatabaseManager object
	DatabaseManager dbManager;

	/**
	 * Constructor of EmailReminder
	 */
	public EmailReminder() {
		appSet = new HashSet<Appointment>();
		dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * Gets the email and appointment details
	 */
	public void getList() {
		appSet.clear();
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		String termID = dbManager.getTermByDate(c.getTime()).getTermID();
		int reminderInterval = dbManager.R_getTestCenterInfo(termID)
				.getReminderInterval();
		List<Appointment> todayApps = dbManager.R_getAppointmentOnDate(c
				.getTime());
		for (Appointment a : todayApps) {
			if (!a.getIfEmailed()) {
				c = Calendar.getInstance();
				Date appStart = a.getTimeStart();
				c2.setTime(appStart);
				// check if appointment starts in reminderInterval
				c.add(Calendar.MINUTE, reminderInterval);
				// I think equals would be too exact
				if (c.after(c2)) {
					appSet.add(a);
				}
			}
		}
	}

	@Override
	public void run() {
		boolean dbClosed = dbManager.checkClosedEntityManager();
		if (dbClosed) {
			dbManager.createEntityManager();
		}
		getList();
		final String username = "cse308.team5@gmail.com";
		final String password = "teamfive5";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		for (Appointment a : appSet) {
			// this won't be actually used
			String stu_email = a.getUser().getEmail();

			// for testing, set recipient to be self
			String recp_email = "cse308.team5@gmail.com";

			String message_txt = "Dear Student,\n\nYou have an appointment. Please show up. "
					+ "Details are as follows:\n\n"
					+ "Name: "
					+ a.getUser().getFirstName()
					+ " "
					+ a.getUser().getLastName()
					+ "\n\nExam: "
					+ a.getRequest().getExamName()
					+ "\n\nAppointment Time: "
					+ a.getTimeStart() + "\n\nSeat Number: " + a.getSeatNum();

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recp_email));
				message.setSubject("Appointment Reminder");
				message.setText(message_txt);
				Transport.send(message);
				LoggerWrapper.logger.info("Email sends to student "
						+ a.getUser().getNetID());
				dbManager.setAppEmailed(a);
			} catch (MessagingException e) {
				LoggerWrapper.logger.warning(e.getClass() + ":"
						+ e.getMessage());
				throw new RuntimeException(e);
			}
		}

		if (dbClosed) {
			dbManager.closeEntityManager();
		}
	}
}
