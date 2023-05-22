package com.reza.srms.repositories;

import com.reza.srms.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    Optional<Course> findByCourseCode(String courseCode);
    Optional<Course> findByCourseCodeAndCourseCodeNot(String newCourseCode, String previousCourseCode);
    Optional<Course> findByCourseTitle(String courseTitle);
    Optional<Course> findByCourseTitleAndCourseTitleNot(String newCourseTitle, String previousCourseTitle);


    @Query(value = """
            SELECT c.* FROM course AS c
                WHERE
                (:search IS NULL OR :search = '') OR
                (	
                	LOWER(c.course_code) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(c.course_title) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            ORDER BY c.course_id DESC
            """,
            nativeQuery = true,
            countQuery = """
                        SELECT COUNT(c.course_id) FROM SRMS.course AS c
                        WHERE
                        (:search IS NULL OR :search = '') OR
                        (	
                        	LOWER(c.course_code) LIKE LOWER(CONCAT('%', :search, '%')) OR
                            LOWER(c.course_title) LIKE LOWER(CONCAT('%', :search, '%'))
                        )
                    """
    )
    Page<Course> getList(String search, Pageable pageable);

}