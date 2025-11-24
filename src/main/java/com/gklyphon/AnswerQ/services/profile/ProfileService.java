package com.gklyphon.AnswerQ.services.profile;

import com.gklyphon.AnswerQ.dtos.EmailUpdateDto;
import com.gklyphon.AnswerQ.dtos.PasswordUpdateDto;
import com.gklyphon.AnswerQ.dtos.ResponseUserDto;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.mapper.IMapper;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.repositories.IUserRepository;
import com.gklyphon.AnswerQ.services.email.EmailService;
import com.gklyphon.AnswerQ.services.security.AuthenticationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service responsible for handling profile-related operations for {@link User} accounts.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final IUserRepository userRepository;
    private final IMapper mapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(IUserRepository userRepository, IMapper mapper, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves user profile details by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the mapped {@link ResponseUserDto} containing profile information
     * @throws ElementNotFoundException if the user does not exist
     */
    @Transactional
    public ResponseUserDto userDetails(Long id) throws ElementNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("User not found"));
        return mapper.fromUserToUserDto(user);
    }

    /**
     * Updates a user's password after validating:
     *
     * @param id the ID of the user
     * @param passwordUpdateDto DTO containing old, new, and confirmation passwords
     * @throws ElementNotFoundException if the user does not exist
     * @throws IllegalArgumentException if validation constraints fail
     */
    @Transactional
    public void updatePassword(Long id, PasswordUpdateDto passwordUpdateDto) throws ElementNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        if (passwordEncoder.matches(passwordUpdateDto.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        userRepository.save(user);
        sendUpdatePasswordNotification(user);
    }


    /**
     * Updates a user's email address after validating:
     *
     * @param id the ID of the user
     * @param emailUpdateDto DTO containing the new email and password validation
     * @throws ElementNotFoundException if the user does not exist
     * @throws IllegalArgumentException if password is invalid or email is already used
     */
    @Transactional
    public void updateEmail(Long id, EmailUpdateDto emailUpdateDto) throws ElementNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));

        if (!passwordEncoder.matches(emailUpdateDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (userRepository.existsByEmail(emailUpdateDto.getNewEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        user.setEmail(emailUpdateDto.getNewEmail());
        user.setEnabled(false);
        userRepository.save(user);
        sendUpdateEmailNotification(user);
    }


    /**
     * Sends a notification email confirming an email update.
     *
     * @param user the user who updated their email
     */
    private void sendUpdateEmailNotification(User user) {
        String subject = "Email Update";
        String htmlMessage = "<html>" +
                "<body style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333333; margin: 0; padding: 0;\">" +
                "<div style=\"max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); overflow: hidden;\">" +
                "<div style=\"background-color: #4a6bff; padding: 25px; text-align: center;\">" +
                "<h1 style=\"color: white; margin: 0; font-size: 24px;\">Email Update Confirmed</h1>" +
                "</div>" +
                "<div style=\"padding: 25px;\">" +
                "<p>Hello <strong>" + user.getFirstname() + "</strong>,</p>" +
                "<p>We're confirming that the email address associated with your account has been successfully updated.</p>" +
                "<div style=\"background-color: #f8f9ff; border-left: 4px solid #4a6bff; padding: 15px; margin: 20px 0; border-radius: 0 4px 4px 0;\">" +
                "<p style=\"margin: 5px 0;\"><strong>Update timestamp:</strong> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")) + "</p>" +
                "<p style=\"margin: 5px 0;\"><strong>New email address:</strong> " + user.getEmail() + "</p>" +
                "</div>" +
                "<div style=\"background-color: #fff8f8; border-left: 4px solid #ff4a4a; padding: 15px; margin: 20px 0; border-radius: 0 4px 4px 0;\">" +
                "<h3 style=\"margin-top: 0; color: #d32f2f;\">Didn't make this change?</h3>" +
                "<p>If you didn't update your email address, please take these steps immediately:</p>" +
                "<ol style=\"padding-left: 20px; margin: 10px 0;\">" +
                "<li>Change your account password</li>" +
                "<li>Review your account security settings</li>" +
                "</ol>" +
                "<p style=\"margin-bottom: 0;\"><a href=\"#\" style=\"color: #d32f2f; font-weight: bold;\">Secure your account now</a></p>" +
                "</div>" +
                "<p>Thank you for using our services.</p>" +
                "<p>The <strong>AnswerQ</strong> Team</p>" +
                "</div>" +
                "<div style=\"background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 12px; color: #777777;\">" +
                "<p>This is an automated message - please do not reply directly to this email.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        try {
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Sends a notification email confirming a password update.
     *
     * @param user the user who updated their password
     */

    private void sendUpdatePasswordNotification(User user) {
        String subject = "Email Update";
        String htmlMessage = "<html>" +
                "<body style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333333; margin: 0; padding: 0;\">" +
                "<div style=\"max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); overflow: hidden;\">" +
                "<div style=\"background-color: #4a6bff; padding: 25px; text-align: center;\">" +
                "<h1 style=\"color: white; margin: 0; font-size: 24px;\">Password Update Confirmed</h1>" +
                "</div>" +
                "<div style=\"padding: 25px;\">" +
                "<p>Hello <strong>" + user.getFirstname() + "</strong>,</p>" +
                "<p>We're confirming that the password of your account has been successfully updated.</p>" +
                "<div style=\"background-color: #f8f9ff; border-left: 4px solid #4a6bff; padding: 15px; margin: 20px 0; border-radius: 0 4px 4px 0;\">" +
                "<p style=\"margin: 5px 0;\"><strong>Update timestamp:</strong> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")) + "</p>" +
                "</div>" +
                "<div style=\"background-color: #fff8f8; border-left: 4px solid #ff4a4a; padding: 15px; margin: 20px 0; border-radius: 0 4px 4px 0;\">" +
                "<h3 style=\"margin-top: 0; color: #d32f2f;\">Didn't make this change?</h3>" +
                "<p>If you didn't update your email address, please take these steps immediately:</p>" +
                "<ol style=\"padding-left: 20px; margin: 10px 0;\">" +
                "<li>Review your account security settings</li>" +
                "</ol>" +
                "<p style=\"margin-bottom: 0;\"><a href=\"#\" style=\"color: #d32f2f; font-weight: bold;\">Secure your account now</a></p>" +
                "</div>" +
                "<p>Thank you for using our services.</p>" +
                "<p>The <strong>AnswerQ</strong> Team</p>" +
                "</div>" +
                "<div style=\"background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 12px; color: #777777;\">" +
                "<p>This is an automated message - please do not reply directly to this email.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        try {
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }

}
