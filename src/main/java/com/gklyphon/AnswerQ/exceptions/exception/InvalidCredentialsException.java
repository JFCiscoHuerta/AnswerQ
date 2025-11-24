package com.gklyphon.AnswerQ.exceptions.exception;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
