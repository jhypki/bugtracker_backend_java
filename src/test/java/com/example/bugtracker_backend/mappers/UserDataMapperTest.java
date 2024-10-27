package com.example.bugtracker_backend.mappers;

import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDataMapperTest {

    private User user;
    private UserData userData;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setSecondName("Doe");
        user.setEmail("test@example.com");
        user.setRole(null);

        userData = new UserData(1, "John", "Doe", "test@example.com", null);
    }

    @Test
    void toUserData_UserExists_ReturnsUserData() {
        // Act
        UserData result = UserDataMapper.toUserData(user);

        // Assert
        assertEquals(userData.getId(), result.getId());
        assertEquals(userData.getFirstName(), result.getFirstName());
        assertEquals(userData.getLastName(), result.getLastName());
        assertEquals(userData.getEmail(), result.getEmail());
        assertEquals(userData.getRole(), result.getRole());
    }

    @Test
    void toUser_UserDataExists_ReturnsUser() {
        // Act
        User result = UserDataMapper.toUser(userData);

        // Assert
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getSecondName(), result.getSecondName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test
    void toUserData_UserIsNull_ReturnsNull() {
        // Arrange
        user = null;

        // Act
        UserData result = UserDataMapper.toUserData(user);

        // Assert
        assertNull(result);
    }

    @Test
    void toUser_UserDataIsNull_ReturnsNull() {
        // Arrange
        userData = null;

        // Act
        User result = UserDataMapper.toUser(userData);

        // Assert
        assertNull(result);
    }
}
