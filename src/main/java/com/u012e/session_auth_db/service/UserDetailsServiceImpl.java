package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.model.Student;
import com.u012e.session_auth_db.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );

        return new User(
                student.getUsername(),
                student.getPassword(),
                new ArrayList<>()
        );
    }
}
