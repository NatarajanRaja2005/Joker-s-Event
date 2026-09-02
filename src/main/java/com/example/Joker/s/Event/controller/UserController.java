package com.example.Joker.s.Event.controller;


import com.example.Joker.s.Event.Event.model.Users;
import com.example.Joker.s.Event.Event.response.ApiResponse;
import com.example.Joker.s.Event.dto.UserDto;
import com.example.Joker.s.Event.request.AddUserRequest;
import com.example.Joker.s.Event.request.UpdateUserRequest;
import com.example.Joker.s.Event.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {

    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AddUserRequest request){
        try {
            Users user=userService.createUser(request);
            UserDto userDto=userService.userToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User created Successfully.",userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("User creation failed.",e.getMessage()));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,@PathVariable Long userId){
        try {
            Users user=userService.updateUser(request,userId);
            UserDto userDto=userService.userToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated Successfully",userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("User updation failed.",e.getMessage()));
        }
    }

    @GetMapping("/get/id/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            Users user=userService.getUserById(userId);
            UserDto userDto=userService.userToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User retrived by id.",userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("User retrival failed.",e.getMessage()));
        }
    }
}
