package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.LoginDto;
import com.u012e.session_auth_db.dto.RegisterDto;
import com.u012e.session_auth_db.service.AuthenticationService;
import com.u012e.session_auth_db.utils.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public GenericResponse<Map<String, String>> login(
            @RequestBody LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.trace("logging in user");
        authenticationService.login(loginDto, request, response);
        log.trace("user is logged in");
        return GenericResponse.<Map<String, String>>builder()
                .success(true)
                .message("login successfully")
                .build();
    }

    @PostMapping("/register")
    public GenericResponse<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        log.trace("registering user");
        authenticationService.register(registerDto);
        log.trace("registered user");

        return GenericResponse.<Map<String, String>>builder()
                .success(true)
                .message("registered successfully")
                .build();
    }

    @Operation(summary = "logout", description = "logout current user")
    @PostMapping("/logout")
    public void fakeLogout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}