package com.u012e.session_auth_db.service;

import com.u012e.session_auth_db.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void register(UserDto userDto);
    void login(UserDto userDto, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
