package com.example.Joker.s.Event.Event.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    String message;
    Object data;
}
