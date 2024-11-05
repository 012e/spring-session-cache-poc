package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.RegisterDto;

public interface AuthenticationService {
    void register(RegisterDto registerDto);
}
