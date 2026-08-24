package com.example.Joker.s.Event.Event.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
