package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.dto.EventDto;
import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.request.AddEventRequest;
import com.example.Joker.s.Event.dto.UserDto;

import java.util.List;

public interface IEventService {
    Event createEvent(AddEventRequest request);
    Event updateEvent(Long eventId,AddEventRequest request) throws IllegalAccessException;
    void deleteEvent(Long eventId,Long userId) throws IllegalAccessException;

    Event getEventById(Long eventId);

    void invitePeople(Long eventId,List<String> email);
    void permitPeoples(Long eventId);
    void removePeoples(Long eventId,Long toDeleteUserId);


    Ticket bookEvent(Long eventId, Long userId, String ticketType, String date, String time);

    Ticket cancelBookedEvent(Long eventId, Long userId,Long ticketId);


    List<Event> searchAttendingEventByName(String eventName,Long userId);
    List<Event> searchMyEventByName(String eventName,Long userId);

    List<Event> searchOrganizingEventByUserId(Long userId);
    List<Event> searchAttendingEventByUserId(Long userId);

    List<Event> searchAttendingEventByDate(String date,Long userId);
    List<Event> searchMyEventByDate(String date,Long userId);

    EventDto eventDto(Event event);
}
