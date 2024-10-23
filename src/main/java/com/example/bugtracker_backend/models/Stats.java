package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stats")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "solved_bugs", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer solvedBugs;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
