package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.RegisterDto;
import com.u012e.session_auth_db.model.Student;
import com.u012e.session_auth_db.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

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
}
