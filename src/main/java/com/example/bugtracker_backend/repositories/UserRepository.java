package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities.
 * Extends JpaRepository to provide CRUD operations and additional query
 * methods.
 * 
 * Methods:
 * - User findByEmail(String email): Retrieves a User entity by its email.
 * - Boolean existsByEmail(String email): Checks if a User entity exists with
 * the given email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
