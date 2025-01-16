package com.example.bugtracker_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the response returned after a successful authentication.
 * Contains the authentication token and user data.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserData userData;
}
