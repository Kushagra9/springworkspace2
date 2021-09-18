package com.reddit.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.exception.SpringRedditException;
import com.reddit.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

	private JavaMailSender mailSender;
	private MailContentBuilder mailContentBuilder;
	@Async
	public void sendEmail(NotificationEmail notificationEmail) {
		MimeMessagePreparator mimeMessagePreparator=mimeMessage -> {
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom("kailash-7fa6e5@inbox.mailtrap.io");
			mimeMessageHelper.setTo(notificationEmail.getRecepient());
			mimeMessageHelper.setSubject(notificationEmail.getSubject());
			mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()),true);
		};
		try {
		mailSender.send(mimeMessagePreparator);
		log.info("Activation Email Has Been Sent");
		}catch(MailException exception) {
			log.info("tests "+exception.getMessage());
			throw new SpringRedditException("Exception occured while sending mail to "+notificationEmail.getRecepient());
		}
	}
}
