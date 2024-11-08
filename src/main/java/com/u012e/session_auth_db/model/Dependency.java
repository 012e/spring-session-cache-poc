package com.u012e.session_auth_db.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dependencies", uniqueConstraints = {
        @UniqueConstraint(name = "subject_required_subject_unique", columnNames = {"subject_id", "required_subject_id"})
})
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "required_subject_id", nullable = false)
    private Subject requiredSubject;

}
