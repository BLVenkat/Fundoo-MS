package com.bridgelabz.fundoonotes.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean sendMail(String to,String from, String subject, String body) {
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(to);
			mail.setFrom(from);
			mail.setSubject(subject);
			mail.setText(body);
			javaMailSender.send(mail);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
				
	}
}
