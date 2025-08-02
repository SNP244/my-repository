package com.quickdesk.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status= TicketStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @JsonBackReference(value = "createdByRef")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    @JsonBackReference(value = "assignedToRef")
    private User assignedTo;

    private int upvotes;
    private int downvotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

