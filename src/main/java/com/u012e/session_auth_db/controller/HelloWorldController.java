package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.UserDto;
import com.u012e.session_auth_db.service.UserService;
import com.u012e.session_auth_db.utils.GenericResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    private final UserService userService;

    @GetMapping("/secret")
    public GenericResponse<Map<String, String>> hello() {
        var response = GenericResponse.<Map<String, String>>builder()
                .message("Secret data")
                .data(null)
                .success(true)
                .build();
        return response;
    }

    public HelloWorldController(UserService userService) {
        this.userService = userService;
    }
}
