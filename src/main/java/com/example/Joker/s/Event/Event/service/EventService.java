package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.dto.EventDto;
import com.example.Joker.s.Event.Event.enums.EventName;
import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.repository.EventRepository;
import com.example.Joker.s.Event.Event.repository.TicketRepository;
import com.example.Joker.s.Event.dto.UserDto;
import com.example.Joker.s.Event.repository.UserRepository;
import com.example.Joker.s.Event.Event.request.AddEventRequest;
import com.example.Joker.s.Event.Exception.ItemNotExistsException;
import com.example.Joker.s.Event.Notification.IEventNotificationService;
import com.example.Joker.s.Event.service.IUserService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService{

    private final EventRepository eventRepository;
    private final IUserService userService;
    private final IEventNotificationService notificationService;
    private final UserRepository userRepository;
    private final ITicketService ticketService;
    private final TicketRepository ticketRepository;

    @Override
    public Event createEvent(AddEventRequest request) {
        Event event=settingEvent(new Event(),request);
        return eventRepository.save(event);
    }

    private Event settingEvent(Event event,AddEventRequest request){
        Users user=userService.getUserById(request.getUserId());
        event.setEventName(EventName.valueOf(request.getEventName().toUpperCase()));
        event.setDescription(request.getDescription());
        event.setAutoPermit(request.isAutoPermit());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setInvitationUrl(request.getInvitationUrl());
        System.out.println("People:::: "+request.getMax_peoples()+" \n Auto permit: "+request.isAutoPermit());
        event.setMax_Peoples(request.getMax_peoples());
        event.setUser(user);
        event.setTicketType(request.getTicketType());
        event.setVenue(request.getVenue());
        return event;
    }

    @Override
    public Event updateEvent(Long eventId,AddEventRequest request) throws IllegalAccessException {
        Event event=getEventById(eventId);
        if(event.getUser().getId()!=request.getUserId()){
            throw new IllegalAccessException("Invalid access");
        }
        return eventRepository.save(settingEvent(event,request));
    }

    @Override
    public Event getEventById(Long eventId) {
        Optional<Event> event=eventRepository.findById(eventId);
        if(event.isEmpty()){
            throw new ItemNotExistsException("Event is not exists. Invalid event id.");
        }
        return event.get();
    }

    @Override
    public void invitePeople(Long eventId, List<String> email) {
        Event event=getEventById(eventId);
        String message="Welcome to this event...";
        for(String mail:email){
            System.out.println(message+" "+mail);
            //notificationService.notify(mail,message);
        }
    }

    @Transactional
    @Override
    public void permitPeoples(Long eventId) {
        Event event=getEventById(eventId);
        if(!event.isAutoPermit()){
            return;
        }

        List<Ticket> tickets=event.getTickets();
        int capacity=event.getPeoples().size();
        Long maxCapacity=event.getMax_Peoples();

        if(tickets==null || tickets.isEmpty()){
            throw new ItemNotExistsException("Tickets not found: "+eventId);
        }

        if(maxCapacity!=null && capacity>=maxCapacity){
            throw new ItemNotExistsException("Capacity is full!");
        }

        for(Ticket ticket:tickets){
            if(maxCapacity!=null && capacity>=maxCapacity){
                break;
            }
            if(!ticket.isPermitStatus()) {
                //System.out.println("TicketId: "+ticket.getId()+"\nUserName: "+ticket.getUser().getFirstName()+"\nPermit status: "+ticket.isPermitStatus());
                Users user = ticket.getUser();
                ticket.setPermitStatus(true);
                if(user!=null && !event.getPeoples().contains(user)) {
                    event.getPeoples().add(user);
                    user.getAttendeesEvent().add(event);
                    capacity++;
                }
            }
        }
        eventRepository.save(event);
    }

    @Override
    public void removePeoples(Long eventId,Long userId) {
        Event event=getEventById(eventId);
        Users user=userService.getUserById(userId);
        if(!event.getPeoples().contains(user)){
            throw new ItemNotExistsException("User not found!");
        }
        event.getPeoples().remove(user);
        eventRepository.save(event);
    }

    @Override
    public Ticket bookEvent(Long eventId, Long userId, String ticketType, String date, String time) {
        Event event=getEventById(eventId);
        Users user=userService.getUserById(userId);
        return ticketService.createTicket(event,user,ticketType,date,time);
    }

    @Override
    public Ticket cancelBookedEvent(Long eventId, Long userId,Long ticketId) {
        Users user=userService.getUserById(userId);
        Ticket ticket=ticketService.getTicketById(ticketId);
        if(!user.getAttendeesEvent().contains(ticket)){
            throw new ItemNotExistsException("Invalid ticket Id");
        }
        return ticketService.cancelTicket(ticket);
    }

    @Override
    public void deleteEvent(Long eventId, Long userId) throws IllegalAccessException {
        Event event=getEventById(eventId);
        if(event.getUser().getId()!=userId){
            throw new IllegalAccessException("Invalid Access Found!");
        }
        eventRepository.delete(event);
    }

    @Override
    public List<Event> searchAttendingEventByName(String eventName,Long userId) {
        Users user=userService.getUserById(userId);
        List<Event> events=user.getAttendeesEvent();
        return events.stream()
                .filter(event -> event.getEventName()!=null && event.getEventName().toString().equalsIgnoreCase(eventName))
                .toList();
    }

    @Override
    public List<Event> searchMyEventByName(String eventName, Long userId) {
        Users user=userService.getUserById(userId);
        List<Event> events=user.getOrganizedEvents();

        return events.stream()
                .filter(event -> event.getEventName()!=null && event.getEventName().toString().equalsIgnoreCase(eventName))
                .toList();
    }

    @Override
    public List<Event> searchOrganizingEventByUserId(Long userId) {
        Users user=userService.getUserById(userId);
        return user.getOrganizedEvents();
    }

    @Override
    public List<Event> searchAttendingEventByUserId(Long userId) {
        Users user=userService.getUserById(userId);
        return user.getAttendeesEvent();
    }

    @Override
    public List<Event> searchAttendingEventByDate(String date,Long userId) {
        Users user=userService.getUserById(userId);
        LocalDate searchDate=LocalDate.parse(date);
        List<Event> events=user.getAttendeesEvent();
        return events.stream()
                .filter(event -> !searchDate.isAfter(event.getEndDate())&&
                        !searchDate.isBefore(event.getStartDate()))
                .toList();
    }

    @Override
    public List<Event> searchMyEventByDate(String date, Long userId) {
        Users user=userService.getUserById(userId);
        LocalDate searchDate=LocalDate.parse(date);
        List<Event> events=user.getOrganizedEvents();
        return events.stream()
                .filter(event -> !searchDate.isAfter(event.getEndDate())&&
                        !searchDate.isBefore(event.getStartDate()))
                .toList();
    }

    @Override
    public EventDto eventDto(Event event){
        EventDto eventDto=new EventDto();
        eventDto.setId(event.getId());
        UserDto userDto=userService.userToUserDto(event.getUser());
        eventDto.setUserDto(userDto);
        eventDto.setEventName(event.getEventName());
        eventDto.setDescription(event.getDescription());
        eventDto.setAutoPermit(event.isAutoPermit());
        eventDto.setStartDate(event.getStartDate());
        eventDto.setEndDate(event.getEndDate());
        eventDto.setStartTime(event.getStartTime());
        eventDto.setEndTime(event.getEndTime());
        eventDto.setVenue(event.getVenue());
        eventDto.setMax_Peoples(event.getMax_Peoples());
        eventDto.setInvitationUrl(event.getInvitationUrl());

        return eventDto;
    }

}
