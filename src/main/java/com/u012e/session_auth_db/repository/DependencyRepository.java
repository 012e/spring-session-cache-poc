package com.u012e.session_auth_db.repository;

import com.u012e.session_auth_db.model.Dependency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyRepository extends CrudRepository<Dependency, Long> {
}
