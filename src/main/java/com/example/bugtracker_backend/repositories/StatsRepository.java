package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Integer> {
    List<Stats> findTop10ByOrderBySolvedBugsDesc();
}
