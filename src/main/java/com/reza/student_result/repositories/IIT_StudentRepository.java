package com.reza.student_result.repositories;

import com.reza.student_result.entities.IIT_Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IIT_StudentRepository extends JpaRepository<IIT_Student, Long> {
    @Query("SELECT iitStd FROM IIT_Student iitStd WHERE " +
            "(iitStd.roll = :roll) AND (iitStd.recordStatus <> 'DELETED')"
    )
    Optional<IIT_Student> findByRoll(Long roll);
    @Query("SELECT iitStd FROM IIT_Student iitStd WHERE " +
            "(iitStd.id = :id) AND (iitStd.recordStatus <> 'DELETED')"
    )
    Optional<IIT_Student> findStudentById(Long id);
    @Query("SELECT iitStd FROM IIT_Student iitStd WHERE " +
            "(iitStd.id = :id)"
    )
    Optional<IIT_Student> findById(Long id);
}