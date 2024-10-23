package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

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

    private String firstName;

    private String secondName;

    private String email;

    private String passwordHash;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = true)
//    private UsersRole role;
}
