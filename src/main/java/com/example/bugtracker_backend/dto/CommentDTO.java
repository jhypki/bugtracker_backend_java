package com.example.bugtracker_backend.dto;

/**
 * A Data Transfer Object (DTO) for transferring comment data.
 * This record encapsulates a single comment as a string.
 *
 * @param comment the comment text
 */
public record CommentDTO(String comment) {
}
