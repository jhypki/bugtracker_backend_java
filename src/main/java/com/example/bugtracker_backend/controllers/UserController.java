package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email")
    public Optional<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
