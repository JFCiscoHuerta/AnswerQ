package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.dtos.LoginUserDto;
import com.gklyphon.AnswerQ.dtos.RegisterUserDto;
import com.gklyphon.AnswerQ.dtos.ResponseUserDto;
import com.gklyphon.AnswerQ.dtos.VerifyUserDto;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.responses.LoginResponse;
import com.gklyphon.AnswerQ.services.jwt.JwtService;
import com.gklyphon.AnswerQ.services.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication related operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthRestController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Registers a new user account.
     *
     * @param registerUserDto DTO containing user registration details
     * @return ResponseEntity containing the registered user and HTTP status
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        ResponseUserDto registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginUserDto DTO containing user login credentials
     * @return ResponseEntity containing the JWT token and its expiration time
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getJwtExpiration(), authenticatedUser.isEnabled());
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Verifies a user's account using the verification code.
     *
     * @param verifyUserDto DTO containing verification details
     * @return ResponseEntity with success message or error details
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Resends the verification code to the user's email.
     *
     * @param email The email address to resend the code to
     * @return ResponseEntity with success message or error details
     */
    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
