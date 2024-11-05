package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.dto.LoginDto;
import com.u012e.session_auth_db.dto.RegisterDto;
import com.u012e.session_auth_db.service.AuthenticationServiceImpl;
import com.u012e.session_auth_db.utils.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
@Slf4j
public class AuthenticationController {
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/login")
    public GenericResponse<Map<String, String>> login(
            @RequestBody LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken token =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginDto.getUsername(), loginDto.getPassword()
                );

        log.trace("authenticating user");
        Authentication authentication = authenticationManager.authenticate(token);
        log.trace("user authenticated");

        // setup security context holder
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        log.trace("setup database context holder");

        return GenericResponse.<Map<String, String>>builder()
                .success(true)
                .message("login successfully")
                .build();
    }

    @PostMapping("/register")
    public GenericResponse<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        log.trace("registering user");
        authenticationServiceImpl.register(registerDto);
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