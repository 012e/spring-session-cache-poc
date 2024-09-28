package com.u012e.session_auth_db.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    @NotNull
    @Length(min = 32, max = 32)
    private String token;

    @NotNull
    @Length(min = 3, max = 20)
    private String username;
}
