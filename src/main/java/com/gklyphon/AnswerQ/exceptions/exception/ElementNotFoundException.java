package com.gklyphon.AnswerQ.exceptions.exception;

/**
 * Exception thrown when a requested element or resource cannot be found.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class ElementNotFoundException extends Exception {

    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
