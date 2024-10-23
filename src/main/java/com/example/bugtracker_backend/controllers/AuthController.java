package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.services.UserService;
import com.example.bugtracker_backend.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User savedUser = userService.registerNewUser(registerRequest);

        String jwtToken = jwtUtils.generateToken(savedUser.getEmail());

        UserData userData = new UserData(savedUser.getFirstName(), savedUser.getSecondName(), savedUser.getEmail());

        AuthenticationResponse response = new AuthenticationResponse(jwtToken, userData);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User foundUser = userService.loginUser(loginRequest);

        String jwtToken = jwtUtils.generateToken(foundUser.getEmail());

        UserData userData = new UserData(foundUser.getFirstName(), foundUser.getSecondName(), foundUser.getEmail());

        AuthenticationResponse response = new AuthenticationResponse(jwtToken, userData);

        return ResponseEntity.ok(response);
    }
}
