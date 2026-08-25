package com.example.Joker.s.Event.Event.repository;

import com.example.Joker.s.Event.Event.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Ticket findByEventIdAndUserId(long id, Long id1);
}
