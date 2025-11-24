package com.gklyphon.AnswerQ.exceptions.exception;

/**
 * Exception thrown when an account-related operation is attempted
 * but the user's account has not yet been verified.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class AccountNotVerifiedException extends RuntimeException {
    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
