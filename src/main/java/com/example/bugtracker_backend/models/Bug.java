package com.example.bugtracker_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
