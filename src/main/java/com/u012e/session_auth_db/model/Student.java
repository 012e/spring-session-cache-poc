package com.u012e.session_auth_db.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "students", indexes = @Index(columnList = "username"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column
    private String firstName;

    @NotNull
    @Column
    private String lastName ;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column(unique = true)
    private String username;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
