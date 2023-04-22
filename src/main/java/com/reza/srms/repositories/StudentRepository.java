package com.reza.srms.repositories;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student AS s WHERE " +
            "(:roll IS NULL OR :roll = '' OR s.roll = :roll) AND" +
            "(:name IS NULL OR :name = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
            "(:email IS NULL OR :email = '' OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND" +
            "(:passingYear IS NULL OR s.passingYear = :passingYear ) AND" +
            "(:semesterStatus IS NULL OR s.semesterStatus = :semesterStatus) AND" +
            "(:cgpa IS NULL OR s.cgpa = :cgpa) AND" +
            "(s.recordStatus <> 'DELETED')"
    )
    Page searchStudent(Long roll, String name, String email, Integer passingYear,
                       SemesterStatus semesterStatus, Float cgpa, Pageable pageable);

    @Query("SELECT s FROM Student AS s WHERE " +
            "(s.roll = :roll) AND (s.recordStatus <> 'DELETED')"
    )
    Optional<Student> findByRoll(Integer roll);

    Optional<Student> findByIdAndRecordStatusNot(Long id, RecordStatus deleted);
}