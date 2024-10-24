package com.example.bugtracker_backend.mappers;

import com.example.bugtracker_backend.dto.UserData;
import com.example.bugtracker_backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {

    public static UserData toUserData(User user) {
        if (user == null) {
            return null;
        }
        return new UserData(
                user.getId(),
                user.getFirstName(),
                user.getSecondName(),
                user.getEmail(),
                user.getRole()
        );
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
