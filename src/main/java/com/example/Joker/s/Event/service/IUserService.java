package com.example.Joker.s.Event.service;

import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.request.AddUserRequest;
import com.example.Joker.s.Event.dto.UserDto;
import com.example.Joker.s.Event.request.UpdateUserRequest;

public interface IUserService {
    Users createUser(AddUserRequest request);
    Users updateUser(UpdateUserRequest request,Long userId);

    Users getUserById(Long userId);

    Users getUserByEmailAndPhone(String email, String phone);

    UserDto userToUserDto(Users user);
}
