package com.gklyphon.AnswerQ.exceptions.exception;

/**
 * Exception thrown when an attempt is made to create a user
 * but a user with the same credentials or identifying information
 * already exists in the system.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
