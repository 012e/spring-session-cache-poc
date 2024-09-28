package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.UserDto;
import com.u012e.session_auth_db.service.UserService;
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

    @GetMapping("/")
    public Map<String, String> helloWorld() {
        var map = new HashMap<String, String>();
        map.put("message", "yeyeye");
        return map;
    }

    @GetMapping("/secret")
    public Map<String, String> hello() {
        var map = new HashMap<String, String>();
        map.put("message", "super secret message");
        return map;
    }

    public HelloWorldController(UserService userService) {
        this.userService = userService;
    }
}
