package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.dto.TicketDto;
import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.dto.UserDto;

import java.util.List;

public interface ITicketService {
    Ticket createTicket(Event event, Users user,
                        String ticketType,
                        String date,String time);
    Ticket updateTicket(Long ticketId,String ticketType,
                        String date,String time);

    Ticket cancelTicket(Ticket ticket);

    Ticket getTicketById(Long ticketId);

    Ticket getTicketByEventIdAndUserId(Long eventId,Long userId);

    //For organizers
    List<Ticket> getAllPermittedTicketsForOrganizer(Long eventId);
    List<Ticket> listAllPendingTickets(Long eventId);
    List<Ticket> getAllTickets(Long eventId);

    List<UserDto> getAllEntriedCandidates(Long eventId);
    List<UserDto> searchEntriedAttendeeByName(String name, Long eventId);

    List<Ticket> searchTicketByAttendeeName(String name, Long eventId);

    List<Ticket> searchAttendeeByEmail(String email, Long eventId);
    List<Ticket> searchAttendeeByPhone(Long phone,Long eventId);


    //For attenders
    List<Ticket> getAllPermittedTickets(Long userId);
    List<Ticket> getAllPendingTicketsByUserId(Long userId);
    List<Ticket> getAllTicketsByUserId(Long userId);
    TicketDto entryToEvent(Long eventId, Long userId);

    TicketDto ticketDto(Ticket ticket);
}
