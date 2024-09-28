package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.SessionDto;
import com.u012e.session_auth_db.service.session.manager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class SessionAuthenticatorImpl implements SessionAuthenticator {

    private final SessionManager sessionManager;

    public SessionAuthenticatorImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Optional<String> getUsername(HttpServletRequest request) throws IllegalArgumentException {
        var cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        Optional<String> cookie = Arrays
                .stream(cookies)
                .filter(c -> c.getName().equals("SESSION_TOKEN"))
                .findFirst()
                .map(Cookie::getValue);

        return cookie.flatMap(s -> sessionManager
                .getSession(s)
                .map(SessionDto::getUsername));
    }

    @Override
    public boolean authenticate(HttpServletRequest request) {
        return getUsername(request).isPresent();
    }
}
