package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents a user in the bug tracker system.
 * 
 * <p>
 * This entity is mapped to the "users" table in the database and contains
 * information about the user such as their first name, second name, email,
 * password hash, and role.
 * </p>
 * 
 * <p>
 * Annotations are used to enforce validation constraints and specify
 * database column properties.
 * </p>
 * 
 * <ul>
 * <li>{@code id} - The unique identifier for the user, generated
 * automatically.</li>
 * <li>{@code firstName} - The first name of the user, must not be blank.</li>
 * <li>{@code secondName} - The second name of the user, must not be blank.</li>
 * <li>{@code email} - The email of the user, must be unique, valid, and not
 * blank.</li>
 * <li>{@code passwordHash} - The hashed password of the user, must not be
 * blank.</li>
 * <li>{@code role} - The role of the user, can be null.</li>
 * </ul>
 * 
 * <p>
 * Uses Lombok annotations for boilerplate code reduction:
 * </p>
 * <ul>
 * <li>{@code @Getter} - Generates getter methods for all fields.</li>
 * <li>{@code @Setter} - Generates setter methods for all fields.</li>
 * <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 * <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for
 * all fields.</li>
 * <li>{@code @Builder} - Implements the builder pattern for object
 * creation.</li>
 * </ul>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Second name is required")
    private String secondName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UsersRole role;
}
