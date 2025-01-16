package com.example.bugtracker_backend.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorResponse is a data transfer object (DTO) that encapsulates error
 * details.
 * It includes a timestamp indicating when the error occurred, an error message,
 * and a list of specific error details.
 * 
 * <p>
 * This class is immutable and uses Lombok's @Getter annotation to generate
 * getter methods for all fields.
 * 
 * @author
 */
@Getter
public class ErrorResponse {
    final private LocalDateTime timestamp;
    final private String message;
    final private List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = errors;
    }
}
