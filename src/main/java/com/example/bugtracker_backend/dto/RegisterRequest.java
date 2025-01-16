package com.example.bugtracker_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user registration requests.
 * Contains user details required for registration.
 * 
 * Fields:
 * - firstName: User's first name. Must not be blank.
 * - lastName: User's last name. Must not be blank.
 * - email: User's email address. Must be valid and not blank.
 * - password: User's password. Must not be blank.
 * - captchaToken: Captcha token for verification. Must not be blank.
 */
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Captcha is required")
    private String captchaToken;
}
