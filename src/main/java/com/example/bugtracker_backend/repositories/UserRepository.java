package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}
