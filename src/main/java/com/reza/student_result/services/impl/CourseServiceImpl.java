package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Course;
import com.reza.student_result.repositories.CourseRepository;
import com.reza.student_result.requests.CourseRequest;
import com.reza.student_result.services.CourseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CourseServiceImpl extends CourseService {

    public CourseServiceImpl(CourseRepository courseRepository) {
        super(courseRepository);
    }
    @Override
    @Transactional
    public Course save(CourseRequest courseRequest) {
        Course course = courseRequest.to(courseRequest);
        return courseRepository.save(course);
    }
    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

}
