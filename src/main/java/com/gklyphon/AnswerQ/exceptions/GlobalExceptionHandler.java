package com.gklyphon.AnswerQ.exceptions;

import com.gklyphon.AnswerQ.exceptions.exception.AccountNotVerifiedException;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.exceptions.exception.InvalidCredentialsException;
import com.gklyphon.AnswerQ.exceptions.exception.UserAlreadyExistsException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the entire application.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions thrown when an attempt is made to register a user
     * that already exists in the system.
     *
     * @param ex the thrown {@link UserAlreadyExistsException}
     * @return a {@link ResponseEntity} containing a standardized error response with HTTP 400
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Handles exceptions caused by invalid credentials during authentication.
     *
     * @param ex the thrown {@link InvalidCredentialsException}
     * @return a {@link ResponseEntity} with HTTP 401
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex);
    }

    /**
     * Handles cases where a user attempts to access the system but their account
     * has not yet been verified.
     *
     * @param ex the thrown {@link AccountNotVerifiedException}
     * @return a {@link ResponseEntity} with HTTP 403
     */
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotVerified(AccountNotVerifiedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex);
    }

    /**
     * Handles invalid password attempts originating from Spring Security's
     * {@link BadCredentialsException}.
     *
     * @param ex the thrown exception
     * @return a {@link ResponseEntity} with HTTP 401 and a generic "Invalid password" message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, new Exception("Invalid password"));
    }

    /**
     * Handles validation errors triggered by invalid request payloads.
     *
     * @param ex the thrown {@link MethodArgumentNotValidException}
     * @return a map of field errors with HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(field, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles exceptions thrown when an entity or resource is not found in the system.
     *
     * @param ex the thrown {@link ElementNotFoundException}
     * @return a {@link ResponseEntity} with HTTP 404
     */
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElementNotFoundException(ElementNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Handles internal errors thrown by service-layer components.
     *
     * @param ex the thrown {@link ServiceException}
     * @return a {@link ResponseEntity} with HTTP 500
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * Handles any unexpected, uncaught exceptions.
     *
     * @param ex the thrown generic exception
     * @return a {@link ResponseEntity} with HTTP 500
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * Builds a standardized {@link ErrorResponse} object and wraps it in a
     * {@link ResponseEntity} with the provided HTTP status.
     *
     * @param status the HTTP status to return
     * @param ex     the exception providing the error message
     * @return a {@link ResponseEntity} containing the constructed error response
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

}
