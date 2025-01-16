package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBugId(Integer bugId);
}
