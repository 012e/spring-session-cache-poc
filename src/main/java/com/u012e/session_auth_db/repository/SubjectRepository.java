package com.u012e.session_auth_db.repository;

import com.u012e.session_auth_db.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Long, Subject> {
}
