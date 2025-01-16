package com.example.bugtracker_backend.dto;

import com.example.bugtracker_backend.models.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the data of a user in the bug tracker system.
 * This class is used as a Data Transfer Object (DTO) to transfer user data
 * between different layers of the application.
 * 
 * @author
 * @version 1.0
 * 
 * @param id        the unique identifier of the user
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 * @param email     the email address of the user
 * @param role      the role of the user in the system
 */
@AllArgsConstructor
@Getter
@Setter
public class UserData {
    Integer id;
    String firstName;
    String lastName;
    String email;
    UsersRole role;
}