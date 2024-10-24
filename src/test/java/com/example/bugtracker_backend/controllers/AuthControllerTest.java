package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.exceptions.ConflictException;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.services.UserService;
import com.example.bugtracker_backend.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthControllerTest
{
    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");
    }

    @Test
    void register_ShouldReturnUserDataWithToken() {
        // Arrange
        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setEmail("test@example.com");
        savedUser.setPasswordHash("passwordHash");
        savedUser.setFirstName("John");
        savedUser.setSecondName("Doe");
        savedUser.setRole(null);

        when(userService.registerNewUser(registerRequest)).thenReturn(savedUser);

        when(jwtUtils.generateToken(registerRequest.getEmail())).thenReturn("jwtToken");

        // Act
        ResponseEntity<AuthenticationResponse> response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwtToken", Objects.requireNonNull(response.getBody()).getToken());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getUserData().getId());
        assertEquals("John", Objects.requireNonNull(response.getBody()).getUserData().getFirstName());
        assertEquals("Doe", Objects.requireNonNull(response.getBody()).getUserData().getLastName());
        assertEquals("test@example.com", Objects.requireNonNull(response.getBody()).getUserData().getEmail());
    }

    @Test
    void register_ShouldThrowConflictExceptionWhenUserAlreadyExists() {
        // Arrange
        when(userService.registerNewUser(registerRequest)).thenThrow(new ConflictException("Email is already in use"));

        // Act & Assert
        assertThrows(ConflictException.class, () -> authController.register(registerRequest));
    }

    @Test
    void login_ShouldReturnUserDataWithToken() {
        // Arrange
        User foundUser = new User();
        foundUser.setId(1);
        foundUser.setEmail("test@example.com");
        foundUser.setPasswordHash("passwordHash");
        foundUser.setFirstName("John");
        foundUser.setSecondName("Doe");
        foundUser.setRole(null);

        when(userService.loginUser(loginRequest)).thenReturn(foundUser);

        when(jwtUtils.generateToken(loginRequest.getEmail())).thenReturn("jwtToken");

        // Act
        ResponseEntity<AuthenticationResponse> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwtToken", Objects.requireNonNull(response.getBody()).getToken());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getUserData().getId());
        assertEquals("John", Objects.requireNonNull(response.getBody()).getUserData().getFirstName());
        assertEquals("Doe", Objects.requireNonNull(response.getBody()).getUserData().getLastName());
        assertEquals("test@example.com", Objects.requireNonNull(response.getBody()).getUserData().getEmail());
    }

    @Test
    void login_ShouldThrowConflictExceptionWhenUserAlreadyExists() {
        // Arrange
        when(userService.loginUser(loginRequest)).thenThrow(new ConflictException("Email is already in use"));

        // Act & Assert
        assertThrows(ConflictException.class, () -> authController.login(loginRequest));
    }
}
