package com.u012e.session_auth_db.filter;

import com.u012e.session_auth_db.service.SessionAuthenticator;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(1)
@Slf4j
public class SessionAuthenticationFilter extends OncePerRequestFilter {
    private final static List<String> authenticatedUrls = List.of("/secret");
    private final SessionAuthenticator sessionAuthenticator;

    public SessionAuthenticationFilter(SessionAuthenticator sessionAuthenticator) {
        this.sessionAuthenticator = sessionAuthenticator;

    }

    private boolean needAuthentication(HttpServletRequest httpRequest) {
        return authenticatedUrls.contains(httpRequest.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (needAuthentication(request)) {
            if (!sessionAuthenticator.authenticate(request)) {
                // can't throw exception, controller advices can't catch exception inside filters
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Unauthorized\"}");
                return;
            }
        }
        chain.doFilter(request, response);

    }
}
