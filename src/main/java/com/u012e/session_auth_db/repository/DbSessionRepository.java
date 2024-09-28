package com.u012e.session_auth_db.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.u012e.session_auth_db.model.Session;

import java.util.Optional;

@Repository
public interface DbSessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    @Transactional
    Long deleteByToken(String token);
}
