package com.example.bugtracker_backend.mappers;

import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.models.User;
import org.springframework.stereotype.Component;

/**
 * UserDataMapper is a utility class that provides methods to convert between
 * User and UserData objects.
 * 
 * <p>
 * This class contains static methods to map User objects to UserData objects
 * and vice versa. It ensures that null values are handled gracefully by
 * returning
 * null when the input is null.
 * </p>
 * 
 * <p>
 * Methods:
 * </p>
 * <ul>
 * <li>{@link #toUserData(User)} - Converts a User object to a UserData
 * object.</li>
 * <li>{@link #toUser(UserData)} - Converts a UserData object to a User
 * object.</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * User user = new User(...);
 * UserData userData = UserDataMapper.toUserData(user);
 * 
 * UserData userData = new UserData(...);
 * User user = UserDataMapper.toUser(userData);
 * }
 * </pre>
 * 
 * <p>
 * Note: This class is annotated with {@code @Component} to indicate that it is
 * a Spring
 * component and can be automatically detected and managed by the Spring
 * container.
 * </p>
 * 
 * @see User
 * @see UserData
 */
@Component
public class UserDataMapper {

    public static UserData toUserData(User user) {
        if (user == null) {
            return null;
        }
        return new UserData(user.getId(), user.getFirstName(), user.getSecondName(), user.getEmail(), user.getRole());
    }

    public static User toUser(UserData userData) {
        if (userData == null) {
            return null;
        }
        return User.builder()
                .id(userData.getId())
                .firstName(userData.getFirstName())
                .secondName(userData.getLastName())
                .email(userData.getEmail())
                .role(userData.getRole())
                .build();
    }
}
