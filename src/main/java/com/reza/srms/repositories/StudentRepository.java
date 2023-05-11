package com.reza.srms.repositories;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRoll(Long roll);

    Optional<Student> findById(Long id);

    @Query(value = """
            SELECT s.* FROM student AS s
                WHERE
                	(:search IS NULL OR :search = '' OR
                	LOWER(s.student_name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(s.student_name_bn) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(s.student_email) LIKE LOWER(CONCAT('%', :search, '%')) ) AND
                    (:semesterStatus IS NULL OR :semesterStatus = '' OR LOWER(s.semester_status) = LOWER(:semesterStatus)) AND
                    (:roll IS NULL OR :roll = '' OR s.student_roll = :roll)
            ORDER BY s.student_id DESC
            """,
            nativeQuery = true,
            countQuery = """
                    SELECT COUNT(s.student_id) FROM student AS s
                         WHERE
                         	(:search IS NULL OR :search = '' OR
                         	LOWER(s.student_name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                             LOWER(s.student_name_bn) LIKE LOWER(CONCAT('%', :search, '%')) OR
                             LOWER(s.student_email) LIKE LOWER(CONCAT('%', :search, '%')) ) AND
                             (:semesterStatus IS NULL OR :semesterStatus = '' OR LOWER(s.semester_status) = LOWER(:semesterStatus)) AND
                             (:roll IS NULL OR :roll = '' OR s.student_roll = :roll)
                             """
    )
    Page<Student> getList(SemesterStatus semesterStatus, Long roll, String search, Pageable pageable);

    List<Student> findByBatchAndSemesterStatus(Integer batchNo, SemesterStatus semester);
}