package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.models.Stats;
import com.example.bugtracker_backend.services.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public List<Stats> findTop() {
        return statsService.findTop();
    }
}
