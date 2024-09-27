package com.u012e.session_auth_db.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: create index
@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String token;

    @Column
    private String username;
}
