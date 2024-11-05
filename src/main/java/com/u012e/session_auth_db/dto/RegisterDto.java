package com.u012e.session_auth_db.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
