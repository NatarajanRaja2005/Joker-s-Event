package com.example.Joker.s.Event.Event.repository;

import com.example.Joker.s.Event.Event.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Ticket findByEventIdAndUserId(long id, Long id1);

    List<Ticket> findAllByEventId(Long eventId);

    List<Ticket> findAllByUserId(Long userId);
}
