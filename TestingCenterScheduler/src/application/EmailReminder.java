package application;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * Class that sends email to student
 *
 */
public class EmailReminder implements Runnable {

	public EmailReminder() {
		super();
	}
	@Override
	public void run() {
		final String username = "cse308.team5@gmail.com";
		final String password = "teamfive5";

		// do not actually use student email haha
		String stu_email = "";

		// for testing, set recipient to be self
		String recp_email = "cse308.team5@gmail.com";

		String message_txt = "Dear Student,\n\nYou have an appointment. Please show up.";
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
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
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
