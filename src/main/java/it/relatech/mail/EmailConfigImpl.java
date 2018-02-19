package it.relatech.mail;

import java.sql.Timestamp;
import java.time.Instant;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigImpl implements EmailConfig {

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("ideasfactory@outlook.it");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

	// STO USANDO QUESTO
	@Override
	@Async
	public MailObject sendSimpleMessage(MailObject mailObject) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setSentDate(Timestamp.from(Instant.now()));
			helper.setFrom("ideasfactory@outlook.it");
			helper.setTo(mailObject.getTo());
			helper.setSubject(mailObject.getSubject());
			helper.setText(mailObject.getText());
			emailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailObject;
	}
}