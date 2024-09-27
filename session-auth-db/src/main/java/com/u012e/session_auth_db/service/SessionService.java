package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.SessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface SessionService {
    SessionDto createAndSetSession(String username, HttpServletResponse response);

    Optional<SessionDto> getSession(String token);

    void invalidateSession(HttpServletRequest request, HttpServletResponse response);
}
