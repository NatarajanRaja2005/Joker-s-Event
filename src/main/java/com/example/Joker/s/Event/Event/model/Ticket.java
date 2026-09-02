package com.example.Joker.s.Event.Event.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String ticketType;

    private boolean permitStatus;
    private boolean cancellation;
    private LocalDate ticketRaisedDate=LocalDate.now();

    private boolean entry;
    private LocalDate entryDate;
    private LocalTime entryTime;
}
