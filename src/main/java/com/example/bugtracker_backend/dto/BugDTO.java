package com.example.bugtracker_backend.dto;

import com.example.bugtracker_backend.models.BugStatus;
import com.example.bugtracker_backend.models.Severity;

/**
 * A Data Transfer Object (DTO) representing a bug in the bug tracker system.
 *
 * @param title       the title of the bug
 * @param description a detailed description of the bug
 * @param severity    the severity level of the bug
 * @param assignedTo  the username of the person to whom the bug is assigned
 * @param status      the current status of the bug
 */
public record BugDTO(String title, String description, Severity severity, String assignedTo, BugStatus status) {
}
