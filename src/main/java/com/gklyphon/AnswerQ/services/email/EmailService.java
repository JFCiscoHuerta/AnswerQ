package com.gklyphon.AnswerQ.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service for handling email operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * Sends an email.
     *
     * @param to The recipient email address
     * @param subject The subject of the email
     * @param text The content of the email
     * @throws MessagingException If an error occurs while creating or sending the email
     * @throws IllegalArgumentException If any of the parameters are null or empty
     */
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        validateEmailParameters(to, subject, text);
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw ex;
        }
    }

    /**
     * Validates email parameters before sending.
     *
     * @param to The recipient email address
     * @param subject The subject of the email
     * @param text The content of the email
     * @throws IllegalArgumentException If any parameter is invalid
     */
    private void validateEmailParameters(String to, String subject, String text) {
        if (!StringUtils.hasText(to)) {
            throw new IllegalArgumentException("Recipient email address cannot be null or empty");
        }
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("Email subject cannot be null or empty");
        }
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Email content cannot be null or empty");
        }
        if (!to.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid recipient email format");
        }
    }

}
