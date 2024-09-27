package com.u012e.session_auth_db.service;

import org.springframework.stereotype.Service;

@Service
public class RandomAlphaNumericTokenGenerator implements TokenGenerator {
    private final static String ALPHA_NUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    @Override
    public String generateToken(int length) {
        var  builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (ALPHA_NUMERIC_CHARS.length() * Math.random());
            builder.append(ALPHA_NUMERIC_CHARS.charAt(index));
        }
        return builder.toString();
    }
}
