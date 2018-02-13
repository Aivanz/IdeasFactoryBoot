package it.relatech.mail;

public interface EmailConfig {

	void sendSimpleMessage(String to, String subject, String text);

	MailObject sendSimpleMessage(MailObject mailObject);
}
