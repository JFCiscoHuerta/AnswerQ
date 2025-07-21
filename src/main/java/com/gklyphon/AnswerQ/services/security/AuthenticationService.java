package com.gklyphon.AnswerQ.services.security;

import com.gklyphon.AnswerQ.dtos.LoginUserDto;
import com.gklyphon.AnswerQ.dtos.RegisterUserDto;
import com.gklyphon.AnswerQ.dtos.VerifyUserDto;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.repositories.IUserRepository;
import com.gklyphon.AnswerQ.services.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * Service class for handling user authentication operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
@Service
@Slf4j
public class AuthenticationService {

    private static final int VERIFICATION_CODE_LENGTH = 6;
    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES= 15;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    /**
     * Register a new user.
     *
     * @param registerUserDto DTO containing user registration details
     * @return The newly created user
     * @throws RuntimeException if there's an issue sending the verification email
     */
    public User signup(RegisterUserDto registerUserDto) {
        // Use mapper
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setEmail(registerUserDto.getEmail());
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(VERIFICATION_CODE_EXPIRATION_MINUTES));
        user.setEnabled(false);

        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param loginUserDto DTO containing user login credentials
     * @return The authenticated user
     * @throws RuntimeException if user is not found or account is not verified
     */
    public User authenticate(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        return  user;
    }

    /**
     * Verifies a user's account using the provided verification code.
     *
     * @param verifyUserDto DTO containing verification details
     * @throws RuntimeException if verification code is invalid, expired, or user not found
     */
    public void verifyUser(VerifyUserDto verifyUserDto) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyUserDto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(verifyUserDto.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);

                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }

    }

    /**
     * Resends a verification code to the user's email.
     *
     * @param email The email address of the user
     * @throws RuntimeException if user is not found or account is already verified
     */
    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(VERIFICATION_CODE_EXPIRATION_MINUTES));

            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = String.format("""
            <html>
                <body style="font-family: Arial, sans-serif;">
                    <div style="background-color: #f5f5f5; padding: 20px;">
                        <h2 style="color: #333;">Welcome to our app!</h2>
                        <p style="font-size: 16px;">Please enter the verification code below to continue:</p>
                        <div style="background-color: #333; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                            <h3 style="color: #333;">Verification Code:</h3>
                            <p style="font-size: 18px; font-weight: bold; color: #007bff;">%s</p>
                        </div>
                    </div>
                </body>
            </html>
            """, verificationCode);
        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Generates a random 6-digit verification code.
     *
     * @return The generated verification code as a string
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt((int) Math.pow(10, VERIFICATION_CODE_LENGTH) - 1) +
                (int) Math.pow(10, VERIFICATION_CODE_LENGTH - 1);
        return String.valueOf(code);
    }

}
