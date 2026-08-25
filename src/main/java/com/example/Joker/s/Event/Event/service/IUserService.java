package com.example.Joker.s.Event.Event.service;

import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.request.AddUserRequest;
import org.apache.catalina.User;

public interface IUserService {
    User createUser(AddUserRequest request);
    User updateUser();

    Users getUserById(Long userId);
}
