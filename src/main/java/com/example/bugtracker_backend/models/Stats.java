package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the statistics of a user in the bug tracker system.
 * This entity is mapped to the "stats" table in the database.
 * It contains information about the number of bugs solved by the user.
 * 
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database
 * table.
 * - @Table: Specifies the name of the database table to be used for mapping.
 * - @Getter, @Setter: Lombok annotations to generate getter and setter methods.
 * - @NoArgsConstructor, @AllArgsConstructor: Lombok annotations to generate
 * constructors.
 * - @Builder: Lombok annotation to implement the builder pattern.
 * 
 * Fields:
 * - userId: The unique identifier of the user. This is the primary key and is
 * mapped to the "user_id" column.
 * - solvedBugs: The number of bugs solved by the user. This field is mapped to
 * the "solved_bugs" column and has a default value of 0.
 * - user: The user associated with these statistics. This is a one-to-one
 * relationship and is mapped by the "user_id" column.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stats")
public class Stats {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "solved_bugs", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer solvedBugs;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
