package com.example.bugtracker_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.enable}")
    private String enable;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        if (!enable.equals("true")) {
            return;
        }

        try {
            // Create a MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false); // Set to true if it's HTML content

            // Send the email
            mailSender.send(message);

            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
