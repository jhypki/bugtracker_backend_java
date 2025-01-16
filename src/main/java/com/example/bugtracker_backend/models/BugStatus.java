package com.example.bugtracker_backend.models;

/**
 * Enum representing the status of a bug in the bug tracker system.
 * 
 * <ul>
 * <li>OPENED - The bug has been reported and is awaiting triage or
 * assignment.</li>
 * <li>CLOSED - The bug has been resolved or deemed invalid.</li>
 * <li>IN_PROGRESS - The bug is currently being worked on.</li>
 * <li>DONE - The work on the bug has been completed.</li>
 * </ul>
 */
public enum BugStatus {
    OPENED,
    CLOSED,
    IN_PROGRESS,
    DONE
}
