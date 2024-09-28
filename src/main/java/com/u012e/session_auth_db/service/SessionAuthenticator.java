package com.u012e.session_auth_db.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface SessionAuthenticator {
    Optional<String> getUsername(HttpServletRequest request);
    boolean authenticate(HttpServletRequest request);
}
