package com.u012e.session_auth_db.repository;

import com.u012e.session_auth_db.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
}
