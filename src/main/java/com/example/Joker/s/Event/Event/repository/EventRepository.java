package com.example.Joker.s.Event.Event.repository;

import com.example.Joker.s.Event.Event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
}
