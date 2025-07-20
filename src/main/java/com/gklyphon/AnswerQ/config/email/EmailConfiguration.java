package com.gklyphon.AnswerQ.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration class for setting up and providing a JavaMailSender bean.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
@Configuration
public class EmailConfiguration {

    private final String GMAIL_SMTP_HOST = "smtp.gmail.com";
    private final int GMAIL_SMTP_PORT = 587;
    private final String MAIL_PROTOCOL = "smtp";
    private final String SMTP_AUTH = "true";
    private final String STARTTLS_ENABLE = "true";
    private final String SSL_TRUST = "smtp.gmail.com";

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    /**
     * Creates and configures a JavaMailSender bean for sending emails.
     *
     * @return configured JavaMailSender instance
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        configureMailSender(mailSender);
        configureMailProperties(mailSender);

        return mailSender;
    }

    /**
     * Configures the mail sender properties.
     *
     * @param mailSender The JavaMailSender instance to configure
     */
    private void configureMailSender(JavaMailSenderImpl mailSender) {
        mailSender.setHost(GMAIL_SMTP_HOST);
        mailSender.setPort(GMAIL_SMTP_PORT);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);
    }

    /**
     * Configures the additional mail properties.
     *
     * @param mailSender The JavaMailSender instance to configure properties for
     */
    private void configureMailProperties(JavaMailSenderImpl mailSender) {
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", MAIL_PROTOCOL);
        props.put("mail.smtp.auth", SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS_ENABLE);
        props.put("mail.smtp.ssl.trust", SSL_TRUST);
        props.put("mail.debug", "true");
    }

}
