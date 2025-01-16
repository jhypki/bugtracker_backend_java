package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Bug entities.
 * Extends JpaRepository to provide CRUD operations and query methods for Bug
 * entities.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.example.bugtracker_backend.entities.Bug
 */
@Repository
public interface BugRepository extends JpaRepository<Bug, Integer> {
}