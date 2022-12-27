package com.reza.student_result.repositories;

import com.reza.student_result.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    @Query("SELECT std FROM Student std WHERE " +
            "(std.id = :id) AND (std.recordStatus <> 'DELETED')"
    )
    Optional<Student> findStudentById(Long id);

    Optional<Student> findById(Long id);
    @Query("SELECT std FROM Student std WHERE " +
            "(std.studentRoll = :studentRoll) AND (std.recordStatus <> 'DELETED')"
    )
    Optional<Student> findByStudentRoll(Long studentRoll);

    @Query("SELECT std FROM Student std WHERE " +
            "(:studentRoll IS NULL OR std.studentRoll = :studentRoll) AND" +
            "(:studentName IS NULL OR LOWER(std.studentName) LIKE LOWER(CONCAT('%', :studentName, '%'))) AND" +
            "(:studentEmail IS NULL OR LOWER(std.studentEmail) LIKE LOWER(CONCAT('%', :studentEmail, '%'))) AND" +
            "(:studentResult IS NULL OR LOWER(std.studentResult) LIKE LOWER(CONCAT('%', :studentResult, '%'))) AND" +
            "(std.recordStatus <> 'DELETED')"
    )
    Page<Student> searchStudentInDB(Long studentRoll, String studentName, String studentEmail, String studentResult, Pageable pageable);
}
