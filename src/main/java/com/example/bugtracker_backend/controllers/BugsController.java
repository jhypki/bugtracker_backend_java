package com.example.bugtracker_backend.controllers;

import com.example.bugtracker_backend.dto.BugDTO;
import com.example.bugtracker_backend.dto.CommentDTO;
import com.example.bugtracker_backend.models.Bug;
import com.example.bugtracker_backend.models.Comment;
import com.example.bugtracker_backend.services.BugService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing bugs.
 */
@RestController
@RequestMapping("/api/bugs")
public class BugsController {
    private final BugService bugService;

    /**
     * Constructor for BugsController.
     *
     * @param bugService the service to manage bugs
     */
    public BugsController(BugService bugService) {
        this.bugService = bugService;
    }

    /**
     * GET /api/bugs : Get all bugs.
     *
     * @return a list of all bugs
     */
    @GetMapping
    public List<Bug> getBugs() {
        return bugService.findAll();
    }

    /**
     * PATCH /api/bugs/{id} : Update an existing bug.
     *
     * @param id     the id of the bug to update
     * @param bugDTO the bug data to update
     * @return the updated bug
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Bug> updateBug(@PathVariable Integer id, @RequestBody BugDTO bugDTO) {
        return ResponseEntity.ok(bugService.update(bugDTO, id));
    }

    /**
     * POST /api/bugs/create : Create a new bug.
     *
     * @param bugDTO      the bug data to create
     * @param userDetails the user details of the creator
     * @return the created bug
     */
    @PostMapping("/create")
    public ResponseEntity<Bug> createBug(@RequestBody BugDTO bugDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(bugService.create(bugDTO, userDetails));
    }

    /**
     * GET /api/bugs/{id}/comments : Get comments for a specific bug.
     *
     * @param id the id of the bug
     * @return a list of comments for the bug
     */
    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Integer id) {
        return bugService.getComments(id);
    }

    /**
     * POST /api/bugs/{id}/comments/create : Post a comment on a specific bug.
     *
     * @param id          the id of the bug
     * @param userDetails the user details of the commenter
     * @param commentDTO  the comment data to post
     * @return the posted comment
     */
    @PostMapping("/{id}/comments/create")
    public ResponseEntity<Comment> postComment(@PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(bugService.postComment(id, userDetails, commentDTO.comment()));
    }
}
