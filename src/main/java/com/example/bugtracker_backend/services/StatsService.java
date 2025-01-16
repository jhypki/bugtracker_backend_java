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

@Service
public class StatsService {
    private final StatsRepository statsRepository;
    private final UserRepository userRepository;

    public StatsService(StatsRepository statsRepository, UserRepository userRepository) {
        this.statsRepository = statsRepository;
        this.userRepository = userRepository;
    }

    public List<Stats> findTop() {
        return statsRepository.findTop10ByOrderBySolvedBugsDesc();
    }

    public Stats increment(Integer id) {
        Optional<Stats> stats = statsRepository.findById(id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (stats.isEmpty()) {
            Stats newStats = Stats.builder().user(user).solvedBugs(1).build();
            return statsRepository.save(newStats);
        } else {
            Stats foundStats = stats.get();
            foundStats.setSolvedBugs( foundStats.getSolvedBugs() + 1 );
            return statsRepository.save(foundStats);
        }
    }
}
