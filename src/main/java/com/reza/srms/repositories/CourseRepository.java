package com.reza.srms.repositories;

import com.reza.srms.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    Optional<Course> findByCourseCode(String courseCode);

    Optional<Course> findByCourseTitle(String courseTitle);

//    @Query("SELECT c FROM Course c WHERE " +
//            "(LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :courseCode, '%'))) AND " +
//            "(LOWER(c.courseTitle) LIKE LOWER(CONCAT('%', :courseTitle, '%'))) AND " +
//            "(c.recordStatus <> 'DELETED')"
//    )
//    Page<Course> searchCourse(String courseCode, String courseTitle, Pageable pageable);
}