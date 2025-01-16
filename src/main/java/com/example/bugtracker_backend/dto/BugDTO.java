package com.example.bugtracker_backend.dto;

import com.example.bugtracker_backend.models.BugStatus;
import com.example.bugtracker_backend.models.Severity;

public record BugDTO(String title, String description, Severity severity, String assignedTo, BugStatus status) {
}
