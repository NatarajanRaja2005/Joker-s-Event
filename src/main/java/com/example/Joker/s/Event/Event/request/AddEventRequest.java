package com.example.Joker.s.Event.Event.request;

import com.example.Joker.s.Event.Event.enums.EventName;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddEventRequest {
    private Long userId;
    private String eventName;
    private String description;
    private Long max_Peoples;
    private boolean autoPermit;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;
    private List<String> ticketType;
    private String invitationUrl;
}
