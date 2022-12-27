package com.reza.student_result.services;

import com.reza.student_result.entities.Course;
import com.reza.student_result.entities.Student;
import com.reza.student_result.repositories.CourseRepository;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.requests.CourseRequest;
import com.reza.student_result.requests.StudentRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class CourseService {
    protected final CourseRepository courseRepository;
    protected abstract Course save (CourseRequest courseRequest);
    protected abstract Optional<Course> findByCourseCode(String courseCode);
    protected abstract Optional<Course> findCourseById(Long id);
    protected abstract Optional <Course> findById(Long id);
}
