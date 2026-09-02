package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.dto.TicketDto;
import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.repository.EventRepository;
import com.example.Joker.s.Event.Event.repository.TicketRepository;
import com.example.Joker.s.Event.Exception.AlreadyExistsException;
import com.example.Joker.s.Event.repository.UserRepository;
import com.example.Joker.s.Event.Exception.ItemNotExistsException;
import com.example.Joker.s.Event.dto.UserDto;
import com.example.Joker.s.Event.service.IUserService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final IUserService userService;

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

        return ticket;
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

    @Override
    public Ticket getTicketByEventIdAndUserId(Long eventId, Long userId) {
        return ticketRepository.findByEventIdAndUserId(eventId,userId);
    }

    @Override
    public List<Ticket> getAllPermittedTicketsForOrganizer(Long eventId){
        Event event=getEventById(eventId);
        List<Ticket> tickets=event.getTickets().stream().toList();
        return tickets.stream()
                .filter(Ticket::isPermitStatus)
                .toList();
    }

    @Override
    public List<Ticket> listAllPendingTickets(Long eventId){
        Event event=getEventById(eventId);
        List<Ticket> tickets=event.getTickets().stream().toList();
        return tickets.stream()
                .filter(ticket->!ticket.isPermitStatus())
                .toList();
    }

    @Override
    public List<Ticket> getAllTickets(Long eventId) {
        Event event=getEventById(eventId);
        return ticketRepository.findAllByEventId(eventId);
    }

    @Override
    public List<UserDto> getAllEntriedCandidates(Long eventId) {
        List<Ticket> tickets=getAllTickets(eventId);

        return ticketsToUserDto(tickets.stream()
                .filter(Ticket::isEntry)
                .toList());
    }

    private List<UserDto> ticketsToUserDto(List<Ticket> ticket){
        return ticket.stream()
                .map(Ticket::getUser)
                .map(userService::userToUserDto)
                .toList();
    }

    @Override
    public List<UserDto> searchEntriedAttendeeByName(String name, Long eventId) {
        List<Ticket> tickets=getAllTickets(eventId);
        return tickets.stream()
                .filter(ticket-> ticket
                        .getUser()
                        .getFirstName()
                        .toLowerCase()
                        .startsWith(name))
                .filter(Ticket::isEntry)
                .map(Ticket-> userService.userToUserDto(Ticket.getUser()))
                .toList();
    }

    @Override
    public List<Ticket> searchTicketByAttendeeName(String name, Long eventId){
        List<Ticket> tickets=getAllTickets(eventId);
        return tickets.stream()
                .filter(ticket->ticket.getUser().getFirstName().toLowerCase().startsWith(name))
                .toList();
    }

    @Override
    public List<Ticket> searchAttendeeByEmail(String email, Long eventId) {
        List<Ticket> tickets=getAllTickets(eventId);
        return tickets.stream()
                .filter(ticket->ticket.getUser().getEmail().equalsIgnoreCase(email))
                .toList();
    }

    @Override
    public List<Ticket> searchAttendeeByPhone(Long phone, Long eventId) {
        List<Ticket> tickets=getAllTickets(eventId);
        return tickets.stream()
                .filter(ticket->ticket.getUser().getPhone().equals(String.valueOf(phone)))
                .toList();
    }

    @Override
    public List<Ticket> getAllPermittedTickets(Long userId) {
        List<Ticket> tickets=getAllTicketsByUserId(userId);
        return tickets.stream()
                .filter(Ticket::isPermitStatus)
                .toList();
    }

    @Override
    public List<Ticket> getAllPendingTicketsByUserId(Long userId) {
        List<Ticket> tickets=getAllTicketsByUserId(userId);
        return tickets.stream()
                .filter(ticket-> !ticket.isPermitStatus())
                .toList();
    }

    @Override
    public List<Ticket> getAllTicketsByUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }


    private Event getEventById(Long eventId) {
        Optional<Event> event=eventRepository.findById(eventId);
        if(event.isEmpty()){
            throw new ItemNotExistsException("Event is not exists. Invalid event id.");
        }
        return event.get();
    }

    @Override
    public TicketDto entryToEvent(Long eventId, Long userId){
        Ticket ticket=ticketRepository.findByEventIdAndUserId(eventId,userId);
        if(ticket==null){
            throw new ValidationException("Invalid user found.");
        }
        if(!ticket.isPermitStatus()){
            throw new ValidationException("User is still Not Permitted");
        }
        if(ticket.isCancellation()){
            throw new ValidationException("User cancelled the ticket");
        }
        if(ticket.isEntry()){
            throw new AlreadyExistsException("User already entried.");
        }
        ticket.setEntry(true);
        ticket.setEntryDate(LocalDate.now());
        ticket.setEntryTime(LocalTime.now());
        ticketRepository.save(ticket);

        return ticketDto(ticket);
    }

    @Override
    public TicketDto ticketDto(Ticket ticket){
        if(ticket==null){
            throw new ItemNotExistsException("Ticket not found");
        }

        TicketDto ticketDto=new TicketDto();
        Event event=ticket.getEvent();

        UserDto userDto=userService.userToUserDto(ticket.getUser());

        ticketDto.setId(ticket.getId());
        ticketDto.setEntry(ticket.isEntry());
        ticketDto.setTicketRaisedDate(ticket.getTicketRaisedDate());
        ticketDto.setEntryDate(ticket.getEntryDate());
        ticketDto.setEntryTime(ticket.getEntryTime());

        ticketDto.setCancellation(ticket.isCancellation());
        ticketDto.setTicketType(ticket.getTicketType());

        ticketDto.setBookingDate(ticket.getBookingDate());
        ticketDto.setBookingTime(ticket.getBookingTime());
        ticketDto.setPermitStatus(ticket.isPermitStatus());

        //setting event
        ticketDto.setEventId(event.getId());
        ticketDto.setEventName(String.valueOf(event.getEventName()));
        ticketDto.setEventStartTime(event.getStartTime());
        ticketDto.setEventEndTime(event.getEndTime());
        ticketDto.setEventStartDate(event.getStartDate());
        ticketDto.setEventEndDate(event.getEndDate());

        ticketDto.setUser(userDto);

        return ticketDto;
    }
}
