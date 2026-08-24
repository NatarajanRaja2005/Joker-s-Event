package com.example.Joker.s.Event.Event.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    //Organized events
    @OneToMany(mappedBy = "user")
    private List<Event> organizedEvents=new ArrayList<>();

    @ManyToMany(mappedBy = "peoples")
    private List<Event> attendeesEvent=new ArrayList<>();
}
