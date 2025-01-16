package com.example.bugtracker_backend.exceptions;

/**
 * Exception thrown when a conflict occurs in the application.
 * This typically indicates that a requested operation cannot be completed
 * because it would result in an inconsistent or invalid state.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}