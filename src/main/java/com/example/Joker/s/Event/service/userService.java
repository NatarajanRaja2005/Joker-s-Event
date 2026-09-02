package com.example.Joker.s.Event.service;

import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Exception.AlreadyExistsException;
import com.example.Joker.s.Event.Exception.ItemNotExistsException;
import com.example.Joker.s.Event.request.AddUserRequest;
import com.example.Joker.s.Event.dto.UserDto;
import com.example.Joker.s.Event.repository.UserRepository;
import com.example.Joker.s.Event.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class userService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public Users createUser(AddUserRequest request) {
        Users user=userRepository.findByEmailAndPhone(request.getEmail(),request.getPhone());
        if(user!=null){
            throw new AlreadyExistsException("Invalid email or phone...");
        }
        user=new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    @Override
    public Users updateUser(UpdateUserRequest request,Long userId) {
        Users user=getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ItemNotExistsException("User not found!"));
    }

    @Override
    public Users getUserByEmailAndPhone(String email, String phone){
        Users user=userRepository.findByEmailAndPhone(email,phone);
        if(user==null){
            throw new AlreadyExistsException("Invalid email or phone...");
        }
        return user;
    }

    @Override
    public UserDto userToUserDto(Users user) {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
