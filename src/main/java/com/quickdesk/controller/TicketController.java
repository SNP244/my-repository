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
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    //  Create a ticket
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
        ticket.setCreatedBy(user);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepo.save(ticket);
        return ResponseEntity.ok(ticket);
    }

    //  View own tickets
    @GetMapping("/my")
    public List<Ticket> getMyTickets(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
        return ticketRepo.findByCreatedBy(user);
    }

    //  Agent: View all tickets
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    //  Agent: Update ticket status
   @PutMapping("/{ticketId}/status")
public ResponseEntity<?> updateStatus(
        @PathVariable Long ticketId,
        @RequestParam TicketStatus status,
        @AuthenticationPrincipal UserDetails userDetails) {

    Ticket ticket = ticketRepo.findById(ticketId).orElseThrow();
    String currentEmail = userDetails.getUsername();

    boolean isCreator = ticket.getCreatedBy().getEmail().equals(currentEmail);
    boolean isAssigned = ticket.getAssignedTo() != null &&
                          ticket.getAssignedTo().getEmail().equals(currentEmail);

    if (!isCreator && !isAssigned) {
        return ResponseEntity.status(403).body("You are not authorized to update this ticket.");
    }

    ticket.setStatus(status);
    ticket.setUpdatedAt(LocalDateTime.now());
    ticketRepo.save(ticket);

    return ResponseEntity.ok(ticket);
}


    //  Agent: Assign self to ticket
   @PutMapping("/{ticketId}/assign")
public ResponseEntity<?> assignTicket(
        @PathVariable Long ticketId,
        @AuthenticationPrincipal UserDetails userDetails) {

    Ticket ticket = ticketRepo.findById(ticketId).orElseThrow();

    if (ticket.getAssignedTo() != null) {
        return ResponseEntity.badRequest().body("Ticket is already assigned.");
    }

    User agent = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
    ticket.setAssignedTo(agent);
    ticket.setUpdatedAt(LocalDateTime.now());
    ticketRepo.save(ticket);

    return ResponseEntity.ok("Ticket assigned to " + agent.getName());
}
// Agent: View assigned tickets
@GetMapping("/assigned")
public List<Ticket> getAssignedTickets(@AuthenticationPrincipal UserDetails userDetails) {
    User agent = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
    return ticketRepo.findByAssignedTo(agent);
}

}
