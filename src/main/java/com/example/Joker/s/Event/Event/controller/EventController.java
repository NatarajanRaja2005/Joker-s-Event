package com.example.Joker.s.Event.Event.controller;

import com.example.Joker.s.Event.Event.dto.EventDto;
import com.example.Joker.s.Event.Event.model.Event;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.request.AddEventRequest;
import com.example.Joker.s.Event.Event.response.ApiResponse;
import com.example.Joker.s.Event.Event.service.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/event")
public class EventController {
    private final IEventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEvent(@RequestBody AddEventRequest request){
        try {
            Event event=eventService.createEvent(request);
            EventDto eventDto=eventService.eventDto(event);
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully",eventDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event creation failed"));
        }
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Long eventId,
                                                   @RequestBody AddEventRequest request){
        try {
            Event event=eventService.updateEvent(eventId,request);
            EventDto eventDto=eventService.eventDto(event);
            return ResponseEntity.ok(new ApiResponse("Event updated Successfully!",eventDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event updation failed"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteEvent(@RequestParam Long eventId,
                                                   @RequestParam Long userId){
        try {
            eventService.deleteEvent(eventId,userId);
            return ResponseEntity.ok(new ApiResponse("Event deleted Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event deletion failed"));
        }
    }

    @PostMapping("/invite/eventId/email/")
    public ResponseEntity<ApiResponse> invitePeople(@RequestParam Long eventId,
                                                    @RequestParam List<String> emails){
        try {
            eventService.invitePeople(eventId,emails);
            return ResponseEntity.ok(new ApiResponse("Peoples are invited via email",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event invitation failed"));
        }
    }

    @PutMapping("/permit/all/{eventId}")
    public ResponseEntity<ApiResponse> permitPeople(@PathVariable Long eventId){
        try {
            eventService.permitPeoples(eventId);
            return ResponseEntity.ok(new ApiResponse("Peoples are permitted",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event permission failed"));
        }
    }

    @PutMapping("/permit/remove/id")
    public ResponseEntity<ApiResponse> removePeoples(@RequestParam Long eventId
            ,@RequestParam Long userId){
        try {
            eventService.removePeoples(eventId,userId);
            return ResponseEntity.ok(new ApiResponse("User is removed Successfully.",null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Permit removal failed"));
        }
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse> bookEvent(@RequestParam Long eventId,
                                                 @RequestParam Long userId,
                                                 @RequestParam String ticketType,
                                                 @RequestParam String date,
                                                 @RequestParam String time){
        try {
            Ticket ticket=eventService.bookEvent(eventId,userId,ticketType,date,time);
            return ResponseEntity.ok(new ApiResponse("Event Booked Successfully",null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Booking event failed"));
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelBookedEvent(@RequestParam Long eventId,
                                                         @RequestParam Long userId,
                                                         @RequestParam Long ticketId){
        try {
            Ticket ticket=eventService.cancelBookedEvent(eventId,userId,ticketId);
            return ResponseEntity.ok(new ApiResponse("Booked event cancelled Successfully",ticket));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Booking cancellation failed"));
        }
    }

    @GetMapping("/search/attending/name")
    public ResponseEntity<ApiResponse> searchAttendingEventByName(@RequestParam String eventName,
                                                                  @RequestParam Long userId){
        try {
            List<Event> events=eventService.searchAttendingEventByName(eventName,userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

    @GetMapping("/search/organizing/name")
    public ResponseEntity<ApiResponse> searchOrganizingEventByName(@RequestParam String eventName,
                                                                  @RequestParam Long userId){
        try {
            List<Event> events=eventService.searchMyEventByName(eventName,userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

    @GetMapping("/search/attending/user")
    public ResponseEntity<ApiResponse> searchAttendingEventByUserId(@RequestParam Long userId){
        try {
            List<Event> events=eventService.searchAttendingEventByUserId(userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

    @GetMapping("/search/organizing/user")
    public ResponseEntity<ApiResponse> searchOrganizingEventByUserId(@RequestParam Long userId){
        try {
            List<Event> events=eventService.searchOrganizingEventByUserId(userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

    @GetMapping("/search/attending/date")
    public ResponseEntity<ApiResponse> searchAttendingEventByDate(@RequestParam String eventDate,
                                                                  @RequestParam Long userId){
        try {
            List<Event> events=eventService.searchAttendingEventByDate(eventDate,userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

    @GetMapping("/search/organizing/date")
    public ResponseEntity<ApiResponse> searchOrganizingEventByDate(@RequestParam String eventDate,
                                                                   @RequestParam Long userId){
        try {
            List<Event> events=eventService.searchMyEventByDate(eventDate, userId);
            List<EventDto> eventDtos=events.stream()
                    .map(eventService::eventDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Event retrived Successfully!",eventDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Event retrival failed"));
        }
    }

}
