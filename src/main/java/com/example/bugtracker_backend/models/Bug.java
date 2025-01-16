package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a bug in the bug tracking system.
 * 
 * <p>
 * This entity is mapped to the "bugs" table in the database and contains
 * information about a bug, including its title, description, status, creation
 * time, reporter, assignee, and severity.
 * </p>
 * 
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li>{@link Entity} - Specifies that this class is an entity.</li>
 * <li>{@link Table} - Specifies the table name in the database.</li>
 * <li>{@link Id} - Specifies the primary key of the entity.</li>
 * <li>{@link GeneratedValue} - Specifies the generation strategy for the
 * primary key.</li>
 * <li>{@link Column} - Specifies the column name in the database.</li>
 * <li>{@link Enumerated} - Specifies that the field is an enumerated type.</li>
 * <li>{@link ManyToOne} - Specifies a many-to-one relationship with another
 * entity.</li>
 * <li>{@link JoinColumn} - Specifies the foreign key column.</li>
 * <li>{@link Getter}, {@link Setter}, {@link NoArgsConstructor},
 * {@link AllArgsConstructor}, {@link Builder} - Lombok annotations for
 * boilerplate code reduction.</li>
 * </ul>
 * 
 * <p>
 * Fields:
 * </p>
 * <ul>
 * <li>{@code id} - The unique identifier of the bug.</li>
 * <li>{@code title} - The title of the bug.</li>
 * <li>{@code description} - The detailed description of the bug.</li>
 * <li>{@code status} - The current status of the bug (e.g., OPEN, CLOSED).</li>
 * <li>{@code created} - The timestamp when the bug was created.</li>
 * <li>{@code reporter} - The user who reported the bug.</li>
 * <li>{@code assignedTo} - The user to whom the bug is assigned.</li>
 * <li>{@code severity} - The severity level of the bug (e.g., LOW, MEDIUM,
 * HIGH).</li>
 * </ul>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bugs")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BugStatus status;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "reporter")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private Severity severity;
}
