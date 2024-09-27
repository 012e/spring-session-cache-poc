package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.UserDto;
import com.u012e.session_auth_db.exception.InvalidCredentialsException;
import com.u012e.session_auth_db.model.User;
import com.u012e.session_auth_db.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SessionService sessionService;

    public UserServiceImpl(
            UserRepository userRepository,
            ModelMapper modelMapper,
            @Qualifier("postgres") SessionService sessionService
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.sessionService = sessionService;
    }

    @Override
    public void register(UserDto userDto) {
        log.info("Registering user: {}", userDto);
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
    }

    @Override
    public void login(@NotNull @RequestBody UserDto userDto, HttpServletResponse response) throws IllegalArgumentException {
        var user = userRepository
                .findByUsername(userDto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new InvalidCredentialsException();
        }
        sessionService.createAndSetSession(user.getUsername(), response);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        sessionService.invalidateSession(request, response);
    }
}
