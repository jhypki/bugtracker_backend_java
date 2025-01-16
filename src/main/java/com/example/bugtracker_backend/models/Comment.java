package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a comment made by a user on a bug in the bug tracker system.
 * 
 * <p>
 * This entity is mapped to the "comments" table in the database and contains
 * information about the comment, the user who made the comment, the bug the
 * comment is associated with, and the timestamp when the comment was created.
 * </p>
 * 
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li>@Entity - Specifies that the class is an entity and is mapped to a
 * database table.</li>
 * <li>@Table(name = "comments") - Specifies the name of the database table to
 * be used for mapping.</li>
 * <li>@Id - Specifies the primary key of the entity.</li>
 * <li>@GeneratedValue(strategy = GenerationType.IDENTITY) - Provides the
 * specification of generation strategies for the primary keys.</li>
 * <li>@ManyToOne - Specifies a many-to-one relationship between this entity and
 * another entity.</li>
 * <li>@JoinColumn(name = "bug_id") - Specifies the foreign key column for the
 * bug relationship.</li>
 * <li>@JoinColumn(name = "user_id") - Specifies the foreign key column for the
 * user relationship.</li>
 * <li>@Column(name = "comment") - Maps the "comment" field to the "comment"
 * column in the database.</li>
 * <li>@Column(name = "created_at", nullable = false, columnDefinition =
 * "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") - Maps the "createdAt" field to the
 * "created_at" column in the database with a default value of the current
 * timestamp.</li>
 * <li>@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder -
 * Lombok annotations to generate boilerplate code like getters, setters,
 * constructors, and builder pattern methods.</li>
 * </ul>
 * 
 * <p>
 * Fields:
 * </p>
 * <ul>
 * <li>id - The unique identifier for the comment.</li>
 * <li>bug - The bug associated with the comment.</li>
 * <li>user - The user who made the comment.</li>
 * <li>comment - The content of the comment.</li>
 * <li>createdAt - The timestamp when the comment was created.</li>
 * </ul>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bug_id")
    private Bug bug;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
