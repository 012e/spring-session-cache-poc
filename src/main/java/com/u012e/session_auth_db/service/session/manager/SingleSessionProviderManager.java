package com.u012e.session_auth_db.service.session.manager;


import com.u012e.session_auth_db.dto.SessionDto;
import com.u012e.session_auth_db.service.session.provider.SessionProvider;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/// will be configured in SessionManagerConfiguration
public class SingleSessionProviderManager implements com.u012e.session_auth_db.service.session.manager.SessionManager {

    private final SessionProvider provider;

    /// Ordering matters, the first provider that can create a session will be used
    public SingleSessionProviderManager(SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    public SessionDto createSession(String username) {
        return provider.createSession(username);
    }

    @Override
    public Optional<SessionDto> getSession(String token) {
        return provider.getSession(token);
    }

    @Override
    public void invalidateSession(HttpServletRequest request) {
        provider.invalidateSession(request);
    }
}
