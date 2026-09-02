package com.example.Joker.s.Event.Event.dto;

import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.dto.UserDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketDto {
    private Long id;

    private String eventName;
    private Long eventId;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;

    private UserDto user;
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
