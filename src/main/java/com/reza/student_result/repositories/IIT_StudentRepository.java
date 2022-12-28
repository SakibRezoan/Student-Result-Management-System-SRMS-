package com.reza.student_result.repositories;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.enums.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT iitStd FROM IIT_Student iitStd WHERE " +
            "(:roll IS NULL OR iitStd.roll = :roll) AND" +
            "(:name IS NULL OR LOWER(iitStd.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
            "(:studentEmail IS NULL OR LOWER(iitStd.studentEmail) LIKE LOWER(CONCAT('%', :studentEmail, '%'))) AND" +
            "(:passingYear IS NULL OR iitStd.passingYear = :passingYear ) AND" +
            "(:semesterStatus IS NULL OR LOWER(iitStd.semesterStatus) LIKE LOWER(CONCAT('%', :semesterStatus, '%'))) AND"+
            "(:cgpa IS NULL OR iitStd.cgpa = :cgpa) AND"+
            "(iitStd.recordStatus <> 'DELETED')"
    )
    Page searchIIT_StudentInDB(Long roll, String name, String studentEmail, Integer passingYear,
                               StudentStatus semesterStatus, Double cgpa, Pageable pageable);
}