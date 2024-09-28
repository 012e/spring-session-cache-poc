package com.u012e.session_auth_db.service.session.provider;

import com.u012e.session_auth_db.dto.SessionDto;
import com.u012e.session_auth_db.repository.UserRepository;
import com.u012e.session_auth_db.service.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@Service
@Qualifier("cache")
public class RedisSessionProvider implements SessionProvider {
    private static final String AUTH_PREFIX = "auth:";
    private final RedisTemplate<String, String> redisTemplate;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;

    public RedisSessionProvider(RedisTemplate<String, String> redisTemplate, TokenGenerator tokenGenerator, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public SessionDto createSession(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        var token = tokenGenerator.generateToken();
        var dto = new SessionDto(token, username);
        redisTemplate.opsForValue().set(AUTH_PREFIX + token, username);

        return dto;
    }

    @Override
    public Optional<SessionDto> getSession(String token) {
        var username = redisTemplate.opsForValue().get(AUTH_PREFIX + token);
        if (username == null) {
            return Optional.empty();
        }
        return Optional.of(new SessionDto(token, username));
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
        redisTemplate.delete(AUTH_PREFIX + token);
    }
}
