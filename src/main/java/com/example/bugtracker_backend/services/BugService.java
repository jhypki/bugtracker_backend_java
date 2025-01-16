package com.example.bugtracker_backend.services;

import com.example.bugtracker_backend.dto.BugDTO;
import com.example.bugtracker_backend.models.Bug;
import com.example.bugtracker_backend.models.BugStatus;
import com.example.bugtracker_backend.models.Comment;
import com.example.bugtracker_backend.models.User;
import com.example.bugtracker_backend.repositories.BugRepository;
import com.example.bugtracker_backend.repositories.CommentRepository;
import com.example.bugtracker_backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BugService {
    private final BugRepository bugRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final StatsService statsService;
    private final CommentRepository commentRepository;

    public BugService(BugRepository bugRepository, UserRepository userRepository, EmailService emailService, StatsService statsService, CommentRepository commentRepository) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.statsService = statsService;
        this.commentRepository = commentRepository;
    }

    public List<Bug> findAll() {
        return bugRepository.findAll();
    }

    public Bug create(BugDTO bugDTO, UserDetails userDetails) {
        User assignedTo = null;
        if (!bugDTO.assignedTo().isBlank()) {
            assignedTo = userRepository.findByEmail(bugDTO.assignedTo());

            emailService.sendEmail(assignedTo.getEmail(), "New Bug was assigned to you",
                    "Bug " + bugDTO.title() + " was assigned to you");
        }

        User reporter = userRepository.findByEmail(userDetails.getUsername());

        Bug bug = Bug.builder()
                .title(bugDTO.title())
                .description(bugDTO.description())
                .severity(bugDTO.severity())
                .assignedTo(assignedTo)
                .status(BugStatus.OPENED)
                .created(LocalDateTime.now())
                .reporter(reporter)
                .build();

        bug = bugRepository.save(bug);

        return bug;
    }

    public List<Comment> getComments(Integer id) {
        return commentRepository.findByBugId(id);
    }

    public Comment postComment(Integer id, UserDetails userDetails, String text) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        Bug bug = bugRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Comment comment = Comment.builder()
                .user(user)
                .bug(bug)
                .comment(text)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public Bug update(BugDTO bugDTO, Integer id) {
        Bug bug = bugRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (bugDTO.title() != null) {
            bug.setTitle(bugDTO.title());
        }
        if (bugDTO.description() != null) {
            bug.setDescription(bugDTO.description());
        }
        if (bugDTO.severity() != null) {
            bug.setSeverity(bugDTO.severity());
        }
        if (bugDTO.status() != null) {
            bug.setStatus(bugDTO.status());
            if (bugDTO.status().equals(BugStatus.DONE)) {
                try {
                    Integer userId = bug.getAssignedTo().getId();
                    statsService.increment(userId);
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
            }

        }
        if (bugDTO.assignedTo() != null) {
            User assignedTo = userRepository.findByEmail(bugDTO.assignedTo());
            bug.setAssignedTo(assignedTo);

            emailService.sendEmail(assignedTo.getEmail(), "New Bug was assigned to you",
                    "Bug " + bug.getTitle() + " was assigned to you");
        }

        return bugRepository.save(bug);
    }
}
