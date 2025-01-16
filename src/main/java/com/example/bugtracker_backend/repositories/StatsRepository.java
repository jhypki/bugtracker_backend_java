package com.example.bugtracker_backend.repositories;

import com.example.bugtracker_backend.models.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link Stats} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * <p>
 * This repository includes a custom query method to find the top 10 stats
 * ordered by the number of solved bugs in descending order.
 * </p>
 * 
 * @see JpaRepository
 * @see Stats
 */
public interface StatsRepository extends JpaRepository<Stats, Integer> {
    List<Stats> findTop10ByOrderBySolvedBugsDesc();
}
