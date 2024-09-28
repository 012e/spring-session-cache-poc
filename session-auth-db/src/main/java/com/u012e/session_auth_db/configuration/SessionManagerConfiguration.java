package com.u012e.session_auth_db.configuration;

import com.u012e.session_auth_db.service.session.manager.SingleProviderManager;
import com.u012e.session_auth_db.service.session.provider.SessionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionManagerConfiguration {
    private final SessionProvider databaseSessionProvider;
    private final SessionProvider cacheSessionProvider;

    public SessionManagerConfiguration(
           @Qualifier("database") SessionProvider databaseSessionProvider,
           @Qualifier("cache") SessionProvider cacheSessionProvider
    ) {
        this.databaseSessionProvider = databaseSessionProvider;
        this.cacheSessionProvider = cacheSessionProvider;
    }

    @Bean
    @ConditionalOnProperty(name = "session.storage", havingValue = "database-only", matchIfMissing = false)
    public SingleProviderManager databaseSessionManager() {
        return new SingleProviderManager(databaseSessionProvider);
    }

    @Bean
    @ConditionalOnProperty(name = "session.storage", havingValue = "cache-only", matchIfMissing = false)
    public SingleProviderManager cacheSessionManager() {
        return new SingleProviderManager(cacheSessionProvider);
    }
}
