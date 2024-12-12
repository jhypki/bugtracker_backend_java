package com.example.bugtracker_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bugs")
public class BugsController {
    @GetMapping
    public String getBugs() {
        return "All bugs";
    }
}
