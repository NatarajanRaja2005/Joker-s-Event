package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.repository.EventRepository;
import com.example.Joker.s.Event.Event.repository.TicketRepository;
import com.example.Joker.s.Event.Event.repository.UserRepository;
import com.example.Joker.s.Event.Exception.AlreadyExistsException;
import com.example.Joker.s.Event.Exception.ItemNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Ticket createTicket(Event event, Users user, String ticketType, String date, String time) {
        Ticket ticket=ticketRepository.findByEventIdAndUserId(event.getId(),user.getId());
        if(ticket!=null){
            updateTicket(ticket.getId(),ticketType,date,time);
        }
        ticket=new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setBookingDate(LocalDate.parse(date));
        ticket.setBookingTime(LocalTime.parse(time));
        ticket.setPermitStatus(false);
        ticket.setCancellation(false);
        event.getTickets().add(ticket);
        user.getTickets().add(ticket);
        eventRepository.save(event);
        userRepository.save(user);

        return ticketRepository.save(ticket);
    }


    @Override
    public Ticket updateTicket(Long ticketId, String ticketType, String date, String time) {
        Ticket ticket=getTicketById(ticketId);
        ticket.setBookingTime(LocalTime.parse(time));
        ticket.setBookingDate(LocalDate.parse(date));
        ticket.setTicketType(ticketType);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket cancelTicket(Ticket ticket) {
        ticket.setCancellation(true);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long ticketId){
        return ticketRepository.findById(ticketId).orElseThrow(()->new ItemNotExistsException("Ticket Not found!"));
    }
}
