package com.u012e.session_auth_db.configuration.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
@Profile("database")
@EnableJdbcHttpSession
public class DatabaseSessionConfiguration {
}
