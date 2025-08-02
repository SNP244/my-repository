package com.quickdesk.repository;



import com.quickdesk.model.Comment;
import com.quickdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicket(Ticket ticket);
}
