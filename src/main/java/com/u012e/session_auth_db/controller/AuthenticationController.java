package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.UserDto;
import com.u012e.session_auth_db.service.UserService;
import com.u012e.session_auth_db.utils.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public GenericResponse<Map<String, String>> createUser(@NotNull @RequestBody UserDto userDto) {
        userService.register(userDto);

        return GenericResponse.<Map<String, String>>builder()
                .message("user created")
                .data(null)
                .success(true)
                .build();
    }

    @PostMapping("/login")
    public GenericResponse<Map<String, String>> login(@NotNull @RequestBody UserDto userDto, HttpServletResponse response) {
        userService.login(userDto, response);

        return GenericResponse.<Map<String, String>>builder()
                .message("user logged in")
                .data(null)
                .success(true)
                .build();
    }

    @GetMapping("/logout")
    public GenericResponse<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);

        return GenericResponse.<Map<String, String>>builder()
                .message("user logged out")
                .data(null)
                .success(true)
                .build();
    }
}
