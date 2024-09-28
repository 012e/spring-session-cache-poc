package com.u012e.session_auth_db.service;

public interface TokenGenerator {
    String generateToken(int length);
    default String generateToken() {
        return generateToken(32);
    }
}
