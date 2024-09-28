package com.u012e.session_auth_db.service.session.manager;

import com.u012e.session_auth_db.dto.SessionDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/// will be configured in SessionManagerConfiguration
public class WriteBackSessionManager implements SessionManager {
    @Override
    public SessionDto createSession(String username) {
        return null;
    }

    @Override
    public Optional<SessionDto> getSession(String token) {
        return Optional.empty();
    }

    @Override
    public void invalidateSession(HttpServletRequest request) {

    }
}
