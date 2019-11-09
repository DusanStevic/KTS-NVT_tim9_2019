package backend.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import backend.model.User;

@Service
public class EmailService {
	@Autowired
	private Environment environment;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final String EMAIL_SENDER = "spring.mail.username";
	
	private static final String REGISTRATION_CONFIRMATION_EMAIL = "RegistrationConfirmationEmail";
	public void sendRegistrationConfirmationEmail(User user) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(environment.getProperty(EMAIL_SENDER));
		mail.setSubject("Verification mail for user ");
		String uname = Base64.getEncoder().encodeToString(user.getUsername().getBytes());
		mail.setText("Verification url is: http://localhost:8080/api/users/enabled/"+uname+". Click link to verify account!");
		javaMailSender.send(mail);
	}
	
}
