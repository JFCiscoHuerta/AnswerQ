package com.gklyphon.AnswerQ.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response returned by the API when an exception occurs.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class ErrorResponse {

    /**
     * HTTP status associated with the error.
     */
    private HttpStatus status;

    /**
     * Detailed message describing the error.
     */
    private String message;

    /**
     * Timestamp indicating when the error occurred.
     */
    private LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
