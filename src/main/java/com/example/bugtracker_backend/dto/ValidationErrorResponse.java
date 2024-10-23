package com.example.bugtracker_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse {

    final private LocalDateTime timestamp;
    final private String message;
    final private List<String> errors;

    public ValidationErrorResponse(String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
