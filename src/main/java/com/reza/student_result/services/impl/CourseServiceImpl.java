package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
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

    public Course update(CourseRequest request) {
        Optional<Course> course = findCourseById(request.getId());
        request.update(request, course.get());
        return courseRepository.save(course.get());

    }
    @Override
    public Optional<Course> findCourseById(Long id) {
        Optional<Course> course = courseRepository.findCourseById(id);
        if (course.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course was not found for parameter {id = %s}", id));
        }
        return course;
    }
    @Override
    public Optional<Course> findById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course was not found for parameter {id = %s}", id));
        }
        return course;
    }
    public Course update(Long id, RecordStatus status) {
        Optional<Course> course = courseRepository.findById(id);
        course.get().setRecordStatus(status);
        return courseRepository.save(course.get());
    }
    public Optional<Course> findByCourseTitle(String courseTitle) {
        return courseRepository.findByCourseTitle(courseTitle);
    }
}
