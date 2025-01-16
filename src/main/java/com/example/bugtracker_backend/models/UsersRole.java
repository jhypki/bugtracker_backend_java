package com.example.bugtracker_backend.models;

/**
 * Enum representing the different roles a user can have in the bug tracker
 * system.
 * 
 * <ul>
 * <li>{@link #ADMIN} - Represents an administrative user with full access and
 * control over the system.</li>
 * <li>{@link #SUPPORT} - Represents a support user who handles user issues and
 * provides assistance.</li>
 * <li>{@link #PROGRAMMER} - Represents a programmer user who is responsible for
 * fixing bugs and developing features.</li>
 * </ul>
 */
public enum UsersRole {
    ADMIN, SUPPORT, PROGRAMMER
}
