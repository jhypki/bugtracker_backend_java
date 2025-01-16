package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController is a REST controller that handles authentication-related
 * requests.
 * It provides endpoints for user registration and login.
 * 
 * <p>
 * Base URL: /api/auth
 * </p>
 * 
 * <p>
 * Endpoints:
 * </p>
 * <ul>
 * <li>POST /register - Registers a new user</li>
 * <li>POST /login - Authenticates an existing user</li>
 * </ul>
 * 
 * <p>
 * Dependencies:
 * </p>
 * <ul>
 * <li>UserService - Service for handling user-related operations</li>
 * </ul>
 * 
 * <p>
 * Request and Response Models:
 * </p>
 * <ul>
 * <li>RegisterRequest - Request model for user registration</li>
 * <li>LoginRequest - Request model for user login</li>
 * <li>AuthenticationResponse - Response model for authentication results</li>
 * </ul>
 * 
 * @see UserService
 * @see RegisterRequest
 * @see LoginRequest
 * @see AuthenticationResponse
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response = userService.registerUserAccount(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
