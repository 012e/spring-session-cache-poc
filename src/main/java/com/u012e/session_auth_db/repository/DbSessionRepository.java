package com.u012e.session_auth_db.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.u012e.session_auth_db.model.Session;

import java.util.Optional;

@Repository
public interface DbSessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    Long deleteByToken(String token);
}
