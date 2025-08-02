package com.quickdesk.controller;



import com.quickdesk.model.*;
import com.quickdesk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private UserRepository userRepo;

    // Add a comment
    @PostMapping("/{ticketId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long ticketId,
            @RequestBody Comment commentRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow();
        User user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();

        Comment comment = Comment.builder()
                .ticket(ticket)
                .author(user)
                .message(commentRequest.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepo.save(comment);
        return ResponseEntity.ok(comment);
    }

    // View all comments for a ticket
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getComments(@PathVariable Long ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow();
        List<Comment> comments = commentRepo.findByTicket(ticket);
        return ResponseEntity.ok(comments);
    }
}
