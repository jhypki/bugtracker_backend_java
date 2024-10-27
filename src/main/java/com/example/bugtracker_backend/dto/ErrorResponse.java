package com.example.bugtracker_backend.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
