package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.exceptions.ConflictException;
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

public class AuthControllerTest {
    @Mock
    private UserService userService;

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
    void registerUserAccount_ShouldReturnUserDataWithToken() {
        // Arrange
        String token = "jwtToken";
        UserData userData = new UserData(1, "John", "Doe", "test@example.com", null);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, userData);

        when(userService.registerUserAccount(registerRequest)).thenReturn(authenticationResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = authController.registerUser(registerRequest);

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
    void registerUserAccount_ShouldThrowConflictExceptionWhenUserAlreadyExists() {
        // Arrange
        when(userService.registerUserAccount(registerRequest)).thenThrow(new ConflictException("Email is already in use"));

        // Act & Assert
        assertThrows(ConflictException.class, () -> authController.registerUser(registerRequest));
    }

    @Test
    void authenticate_ShouldReturnUserDataWithToken() {
        // Arrange
        String token = "jwtToken";
        UserData userData = new UserData(1, "John", "Doe", "test@example.com", null);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, userData);

        when(userService.authenticateUser(loginRequest)).thenReturn(authenticationResponse);
        // Act
        ResponseEntity<AuthenticationResponse> response = authController.authenticate(loginRequest);

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
    void authenticate_ShouldThrowConflictExceptionWhenUserAlreadyExists() {
        // Arrange
        when(userService.authenticateUser(loginRequest)).thenThrow(new ConflictException("Email is already in use"));

        // Act & Assert
        assertThrows(ConflictException.class, () -> authController.authenticate(loginRequest));
    }
}
