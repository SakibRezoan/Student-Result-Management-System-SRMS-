package com.reza.srms.repositories;

import com.reza.srms.entities.SemesterWiseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SemesterWiseResultRepository extends JpaRepository<SemesterWiseResult, Long> {

    @Query(value = """
            SELECT swr.* FROM semester_wise_result AS swr WHERE
            swr.student_id = :studentId AND LOWER(swr.semester) = LOWER(:semester)
            """,
            nativeQuery = true)
    Optional<SemesterWiseResult> findByStudentIdAndSemester(Long studentId, String semester);
}