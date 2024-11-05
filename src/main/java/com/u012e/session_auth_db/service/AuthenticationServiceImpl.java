package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.LoginDto;
import com.u012e.session_auth_db.dto.RegisterDto;
import com.u012e.session_auth_db.model.Student;
import com.u012e.session_auth_db.repository.StudentRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public void register(RegisterDto registerDto) {
        var optionalStudent = studentRepository.findByUsername(registerDto.getUsername());
        if (optionalStudent.isPresent()) {
            throw new IllegalArgumentException("username already exists");
        }
        var endcodedPassword = passwordEncoder.encode(registerDto.getPassword());
        var student = Student.builder()
                .username(registerDto.getUsername())
                .password(endcodedPassword)
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .build();
        studentRepository.save(student);
    }

    @Override
    public void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {

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
    }
}
