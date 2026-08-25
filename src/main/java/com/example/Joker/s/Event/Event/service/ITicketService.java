package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;

public interface ITicketService {
    Ticket createTicket(Event event, Users user,
                        String ticketType,
                        String date,String time);
    Ticket updateTicket(Long ticketId,String ticketType,
                        String date,String time);

    Ticket cancelTicket(Ticket ticket);

    Ticket getTicketById(Long ticketId);
}
