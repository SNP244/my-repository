package com.quickdesk.model;



import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Ticket> createdTickets = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Ticket> assignedTickets = new ArrayList<>();
}
