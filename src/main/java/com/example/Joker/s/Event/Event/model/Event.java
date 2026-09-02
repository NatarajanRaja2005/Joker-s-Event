package com.example.Joker.s.Event.Event.model;

import com.example.Joker.s.Event.Event.enums.EventName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EventName eventName;

    private String description;
    private Long max_Peoples;

    @ManyToOne
    @JoinColumn(name="organizer_id")
    private Users user;

    @ManyToMany
    @JoinTable(name="event_attendees",
    joinColumns = @JoinColumn(name="event_id"),
    inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<Users> peoples;

    private boolean autoPermit;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;

    @ElementCollection
    private List<String> ticketType;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ticket> tickets=new ArrayList<>();

    private String invitationUrl;
}
