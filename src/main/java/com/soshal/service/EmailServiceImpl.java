package com.soshal.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements Emailservice {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendInvitation(String email, String link) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Join Prject Team Invitation";
        String text = "Click the link to join the project team:" + link;

        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(email);


        try {
            mailSender.send(mimeMessage);

        } catch (MailException e) {
            throw new MailSendException("Failed to send email");

        }


    }
}
