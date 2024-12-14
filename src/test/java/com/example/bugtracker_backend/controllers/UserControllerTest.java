package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.models.UsersRole;
import com.example.bugtracker_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private List<UserData> usersData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usersData = List.of(
                new UserData(1, "John", "Doe", "test@test.com", null),
                new UserData(2, "Jane", "Doe", "test2@test.com", UsersRole.ADMIN)
        );
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(usersData);

        // Act
        ResponseEntity<List<UserData>> response = userController.getAllUsers();

        // Assert
        assertEquals(usersData, response.getBody());
    }

    @Test
    void getUserById_ShouldReturnUserById() {
        // Arrange
        when(userService.getUserById(1)).thenReturn((usersData.get(0)));

        // Act
        ResponseEntity<UserData> response = userController.getUserById(1);

        // Assert
        assertEquals(usersData.get(0), response.getBody());
    }
}
