package com.example.bugtracker_backend.services;

import com.example.bugtracker_backend.dto.AuthenticationResponse;
import com.example.bugtracker_backend.dto.LoginRequest;
import com.example.bugtracker_backend.dto.RegisterRequest;
import com.example.bugtracker_backend.exceptions.BadRequestException;
import com.example.bugtracker_backend.exceptions.ConflictException;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.repositories.UserRepository;
import com.example.bugtracker_backend.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hashed_password");
        user.setFirstName("John");
        user.setSecondName("Doe");
        user.setRole(null);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
    }

    @Test
    void registerUserAccount_ShouldRegisterSuccessfullyAccount() {
        // Arrange
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtUtils.generateToken(user.getEmail())).thenReturn("jwtToken");

        // Act
        AuthenticationResponse registeredUser = userService.registerUserAccount(registerRequest);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getUserData().getEmail());
        assertEquals("John", registeredUser.getUserData().getFirstName());
        assertEquals("Doe", registeredUser.getUserData().getLastName());
        assertNull(registeredUser.getUserData().getRole());
        assertEquals("jwtToken", registeredUser.getToken());

        verify(userRepository).existsByEmail(registerRequest.getEmail());
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserAccount_ShouldThrowException_WhenUserAlreadyExists() {
        // Arrange
        registerRequest.setEmail("test@example.com");

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> {
            userService.registerUserAccount(registerRequest);
        });

        verify(userRepository).existsByEmail(registerRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void authenticateUser_ShouldAuthenticateSuccessfully() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hashed_password");
        user.setFirstName("John");
        user.setSecondName("Doe");
        user.setRole(null);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "hashed_password")).thenReturn(true);
        when(jwtUtils.generateToken(user.getEmail())).thenReturn("jwtToken");

        // Act
        AuthenticationResponse loggedInUserData = userService.authenticateUser(loginRequest);

        // Assert
        assertNotNull(loggedInUserData);
        assertEquals("test@example.com", loggedInUserData.getUserData().getEmail());
        assertEquals("John", loggedInUserData.getUserData().getFirstName());
        assertEquals("Doe", loggedInUserData.getUserData().getLastName());
        assertNull(loggedInUserData.getUserData().getRole());
        assertEquals("jwtToken", loggedInUserData.getToken());
    }

    @Test
    void authenticateUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        loginRequest.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(passwordEncoder.matches("password123", "hashed_password")).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            userService.authenticateUser(loginRequest);
        });
    }

    @Test
    void loginUser_ShouldThrowException_WhenPasswordIsInvalid() {
        // Arrange
        loginRequest.setEmail("test@example.com");

        when(userRepository.findByEmail("text@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "hashed_password")).thenReturn(false);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            userService.authenticateUser(loginRequest);
        });
    }
}
