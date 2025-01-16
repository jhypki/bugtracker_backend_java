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

@RestController
@RequestMapping("/api/bugs")
public class BugsController {
    private final BugService bugService;
    public BugsController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping
    public List<Bug> getBugs() {
        return bugService.findAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Bug> updateBug(@PathVariable Integer id, @RequestBody BugDTO bugDTO) {
        return ResponseEntity.ok(bugService.update(bugDTO, id));
    }

    @PostMapping("/create")
    public ResponseEntity<Bug> createBug(@RequestBody BugDTO bugDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(bugService.create(bugDTO, userDetails));
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Integer id) {
        return bugService.getComments(id);
    }

    @PostMapping("/{id}/comments/create")
    public ResponseEntity<Comment> postComment(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(bugService.postComment(id, userDetails, commentDTO.comment()));
    }
}
