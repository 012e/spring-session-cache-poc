package com.u012e.session_auth_db.service.session.provider;

import com.u012e.session_auth_db.dto.SessionDto;
import com.u012e.session_auth_db.exception.InvalidCredentialsException;
import com.u012e.session_auth_db.model.Session;
import com.u012e.session_auth_db.repository.DbSessionRepository;
import com.u012e.session_auth_db.repository.UserRepository;
import com.u012e.session_auth_db.service.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@Service
@Qualifier("database")
@Slf4j
public class PostgresSessionProvider implements SessionProvider {
    private final DbSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final ModelMapper mapper;

    public PostgresSessionProvider(
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
