package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Comment entities.
 * Extends JpaRepository to provide CRUD operations.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * 
 *      Methods:
 *      - List<Comment> findByBugId(Integer bugId): Retrieves a list of comments
 *      associated with a specific bug ID.
 * 
 * @author
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBugId(Integer bugId);
}
