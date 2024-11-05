package com.u012e.session_auth_db.configuration.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@Profile("cache")
@EnableRedisHttpSession
public class CacheSessionConfiguration {
}
