package com.example.Joker.s.Event.Event.dto;

import com.example.Joker.s.Event.Event.enums.EventName;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDto {
    private long id;
    @Enumerated(EnumType.STRING)
    private EventName eventName;
    private String description;
    private Long max_Peoples;
    private UserDto userDto;
    private boolean autoPermit;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;
    private String invitationUrl;
}
