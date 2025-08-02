package com.quickdesk.repository;



import com.quickdesk.model.Ticket;
import com.quickdesk.model.User;
import com.quickdesk.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedBy(User user);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByAssignedTo(User user);
}

