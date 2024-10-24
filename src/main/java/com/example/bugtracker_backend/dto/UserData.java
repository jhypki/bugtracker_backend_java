package com.example.bugtracker_backend.dto;

import com.example.bugtracker_backend.models.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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