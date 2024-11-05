package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.LoginDto;
import com.u012e.session_auth_db.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    void register(RegisterDto registerDto);
    void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response);
}
