package pl.mariuszk.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

	private static final String SENDER_EMAIL = "doggoloverspbs@gmail.com";

	private final JavaMailSender emailSender;

	public void sendMessage(String addressee, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(SENDER_EMAIL);
		message.setTo(addressee);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
}
