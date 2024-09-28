package com.u012e.session_auth_db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SessionAuthDbApplication implements ApplicationRunner {
	@Value("${session.storage:database-only}")
	private String sessionStorage;

	public static void main(String[] args) {
		SpringApplication.run(SessionAuthDbApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Supported session storage: database-only, cache-only, database-cache");
		log.info("Session storage: {}", sessionStorage);
	}
}
