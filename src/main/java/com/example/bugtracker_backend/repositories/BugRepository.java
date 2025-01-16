package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Integer> {
}