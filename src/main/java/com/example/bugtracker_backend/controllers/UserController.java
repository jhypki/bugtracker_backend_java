package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController is a REST controller that handles HTTP requests related to
 * user operations.
 * It provides endpoints to retrieve all users, retrieve a user by ID, retrieve
 * a user by email,
 * and update a user's role.
 * 
 * Endpoints:
 * - GET /api/users: Retrieves a list of all users.
 * - GET /api/users/{id}: Retrieves a user by their ID.
 * - GET /api/users/email: Retrieves a user by their email.
 * - PATCH /api/users/{id}: Updates a user's role.
 * 
 * @author
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<UserData> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserData> updateUserRole(@PathVariable Integer id, @RequestParam String role) {
        return ResponseEntity.ok(userService.updateUserRole(id, role));
    }
}
