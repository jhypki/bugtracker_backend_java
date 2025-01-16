package com.example.bugtracker_backend.services;

import com.example.bugtracker_backend.models.Stats;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.repositories.StatsRepository;
import com.example.bugtracker_backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing statistics related to users and their solved bugs.
 */
@Service
public class StatsService {

    /**
     * Repository for accessing and managing statistics data.
     */
    private final StatsRepository statsRepository;

    /**
     * Repository for accessing and managing user data.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new StatsService with the specified repositories.
     *
     * @param statsRepository the repository for statistics data
     * @param userRepository  the repository for user data
     */
    public StatsService(StatsRepository statsRepository, UserRepository userRepository) {
        this.statsRepository = statsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the top 10 statistics records ordered by the number of solved bugs
     * in descending order.
     *
     * @return a list of the top 10 statistics records
     */
    public List<Stats> findTop() {
        return statsRepository.findTop10ByOrderBySolvedBugsDesc();
    }

    /**
     * Increments the number of solved bugs for the user with the specified ID.
     * If the statistics record for the user does not exist, a new record is
     * created.
     *
     * @param id the ID of the user
     * @return the updated or newly created statistics record
     * @throws ResponseStatusException if the user with the specified ID is not
     *                                 found
     */
    public Stats increment(Integer id) {
        Optional<Stats> stats = statsRepository.findById(id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (stats.isEmpty()) {
            Stats newStats = Stats.builder().user(user).solvedBugs(1).build();
            return statsRepository.save(newStats);
        } else {
            Stats foundStats = stats.get();
            foundStats.setSolvedBugs(foundStats.getSolvedBugs() + 1);
            return statsRepository.save(foundStats);
        }
    }
}
