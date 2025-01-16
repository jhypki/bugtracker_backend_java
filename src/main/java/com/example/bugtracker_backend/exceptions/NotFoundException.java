package com.example.bugtracker_backend.exceptions;

/**
 * Exception thrown when a requested resource is not found.
 * This exception extends {@link RuntimeException}.
 * 
 * @param message the detail message explaining the reason for the exception
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}