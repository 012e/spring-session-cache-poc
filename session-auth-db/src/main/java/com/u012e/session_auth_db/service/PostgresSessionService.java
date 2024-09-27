package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.SessionDto;
import com.u012e.session_auth_db.exception.InvalidCredentialsException;
import com.u012e.session_auth_db.model.Session;
import com.u012e.session_auth_db.repository.DbSessionRepository;
import com.u012e.session_auth_db.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.Optional;

@Service
@Qualifier("postgres")
@Slf4j
@ConditionalOnProperty(value = "session.storage", havingValue = "database-only", matchIfMissing = false)
public class PostgresSessionService implements SessionService {
    private final DbSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final ModelMapper mapper;

    public PostgresSessionService(
            DbSessionRepository sessionRepository,
            UserRepository userRepository,
            TokenGenerator tokenGenerator,
            ModelMapper mapper
    ) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.mapper = mapper;
    }

    @Override
    public SessionDto createSession(String username) throws IllegalArgumentException {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new InvalidCredentialsException("User not found");
        }
        var token = tokenGenerator.generateToken();
        var dto = new SessionDto(token, username);
        var session = mapper.map(dto, Session.class);

        sessionRepository.save(session);

        return dto;
    }

    @Override
    public Optional<SessionDto> getSession(String token) {
        return sessionRepository
                .findByToken(token)
                .map(session -> mapper.map(session, SessionDto.class));
    }

    @Override
    public void invalidateSession(HttpServletRequest request) {
        var request_cookies = request.getCookies();
        if (request_cookies == null) {
            return;
        }

        var tokenCookie = WebUtils.getCookie(request, "SESSION_TOKEN");
        if (tokenCookie == null) {
            return;
        }
        var token = tokenCookie.getValue();

        sessionRepository.deleteByToken(token);
    }
}
