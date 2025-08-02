package com.quickdesk.model;




import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference(value = "ticketCommentRef")
    private Ticket ticket;

    @ManyToOne
    private User author;

    private String message;

    private LocalDateTime createdAt;
}
