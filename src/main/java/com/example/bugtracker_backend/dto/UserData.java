package com.example.bugtracker_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserData {
    String firstName;
    String lastName;
    String email;
}