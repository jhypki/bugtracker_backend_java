package com.example.bugtracker_backend.exceptions;

/**
 * Exception thrown to indicate that a bad request was made.
 * This exception is typically used to signal that the server cannot or will not
 * process the request
 * due to something that is perceived to be a client error (e.g., malformed
 * request syntax, invalid request message framing, or deceptive request
 * routing).
 *
 * @param message the detail message explaining the reason for the exception
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
