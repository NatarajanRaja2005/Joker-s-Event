package com.example.Joker.s.Event.Event.controller;


import com.example.Joker.s.Event.Event.dto.TicketDto;
import com.example.Joker.s.Event.Event.model.Ticket;
import com.example.Joker.s.Event.Event.response.ApiResponse;
import com.example.Joker.s.Event.Event.service.ITicketService;
import com.example.Joker.s.Event.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/ticket")
public class TicketController {

    private final ITicketService ticketService;

    @GetMapping("/event/permitted/organizer")
    public ResponseEntity<ApiResponse> getAllCompletedEventTicket(@RequestParam Long eventId){
        try {
            List<Ticket> tickets=ticketService.getAllPermittedTicketsForOrganizer(eventId);
            List<TicketDto> ticketDto=tickets.stream()
                    .map(ticketService::ticketDto)
                    .toList();

            return ResponseEntity.ok(new ApiResponse("Ticket retrived Successfully!",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Ticket retrival failed"));
        }
    }

    @GetMapping("/event/permit/pending/organizer")
    public ResponseEntity<ApiResponse> getAllPEndingEventTicket(@RequestParam Long eventId){
        try {
            List<Ticket> tickets=ticketService.listAllPendingTickets(eventId);
            List<TicketDto> ticketDto=tickets.stream()
                    .map(ticketService::ticketDto)
                    .toList();

            return ResponseEntity.ok(new ApiResponse("Ticket retrived Successfully!",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/get/all/entry/{eventId}")
    public ResponseEntity<ApiResponse> getAllEntriedCandidates(@PathVariable Long eventId){
        try {
            List<UserDto> users=ticketService.getAllEntriedCandidates(eventId);
            return ResponseEntity.ok(new ApiResponse("All entried Person Retrived Successfully",users));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/search/entry/people/name")
    public ResponseEntity<ApiResponse> searchEntriedAttendeeByName(@RequestParam Long eventId,
                                                            @RequestParam(name = "userName") String name){
        try {
            List<UserDto> users=ticketService.searchEntriedAttendeeByName(name,eventId);
            return ResponseEntity.ok(new ApiResponse("Peoples retrived Successfully.",users));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/search/people/name")
    public ResponseEntity<ApiResponse> searchAttendeeByName(@RequestParam Long eventId,
                                                            @RequestParam(name = "userName") String name){
        try {
            List<Ticket> ticket=ticketService.searchTicketByAttendeeName(name, eventId);
            List<TicketDto> ticketDto=ticket.stream()
                    .map(ticketService::ticketDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Peoples retrived Successfully.",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/search/people/email")
    public ResponseEntity<ApiResponse> searchAttendeeByEmail(@RequestParam String email,
                                                             @RequestParam Long eventId){
        try {
            List<Ticket> ticket=ticketService.searchAttendeeByEmail(email, eventId);
            List<TicketDto> ticketDto=ticket.stream()
                    .map(ticketService::ticketDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Peoples retrived Successfully.",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/search/people/phone")
    public ResponseEntity<ApiResponse> searchAttendeeByPhone(@RequestParam Long phone,
                                                             @RequestParam Long eventId){
        try{
            List<Ticket> tickets=ticketService.searchAttendeeByPhone(phone, eventId);
            List<TicketDto> ticketDto=tickets.stream()
                    .map(ticketService::ticketDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("Peoples retrived Successfully.",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/permitted/all")
    public ResponseEntity<ApiResponse> getAllPermittedTickets(@RequestParam Long userId){
        try{
            List<Ticket> tickets=ticketService.getAllPermittedTickets(userId);
            List<TicketDto> ticketDto=tickets.stream()
                    .map(ticketService::ticketDto)
                    .toList();

            return ResponseEntity.ok(new ApiResponse("Ticket retrived Successfully!",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @GetMapping("/event/pending/all")
    public ResponseEntity<ApiResponse> getAllPendingTickets(@RequestParam Long userId){
        try{
            List<Ticket> tickets=ticketService.getAllPendingTicketsByUserId(userId);
            List<TicketDto> ticketDto=tickets.stream()
                    .map(ticketService::ticketDto)
                    .toList();

            return ResponseEntity.ok(new ApiResponse("Ticket retrived Successfully!",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Tickets retrival failed"));
        }
    }

    @PutMapping("/event/entry")
    public ResponseEntity<ApiResponse> makeEntry(@RequestParam Long eventId,
                                                 @RequestParam Long userId){
        try {
            TicketDto ticketDto=ticketService.entryToEvent(eventId, userId);
            return ResponseEntity.ok(new ApiResponse("Ticket verified Successfully",ticketDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),"Ticket Verification failed"));
        }
    }
}
