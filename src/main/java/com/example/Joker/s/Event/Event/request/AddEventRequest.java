package com.example.Joker.s.Event.Event.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class  AddEventRequest {
    private Long userId;
    private String eventName;
    private String description;
    private Long max_peoples;
    private boolean autoPermit;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;
    private List<String> ticketType;
    private String invitationUrl;
}
