package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.UserDto;
import com.u012e.session_auth_db.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, String> createUser(@NotNull @RequestBody UserDto userDto) {
        userService.register(userDto);

        var map = new HashMap<String, String>();
        map.put("message", "Created new user");
        return map;
    }

    @PostMapping("/login")
    public Map<String, String> login(@NotNull @RequestBody UserDto userDto, HttpServletResponse response) {
        userService.login(userDto, response);

        var map = new HashMap<String, String>();
        map.put("message", "User logged in");
        return map;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);

        var map = new HashMap<String, String>();
        map.put("message", "User logged out");
        return map;
    }
}
