package com.reza.student_result.services;

import com.reza.student_result.dtos.CourseDto;
import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.repositories.CourseRepository;
import com.reza.student_result.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public Course save(CourseDto courseDto) {
        Course course = courseDto.toEntity(courseDto);
        return courseRepository.save(course);
    }

    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    public Course update(CourseDto request) {
        Optional<Course> course = findCourseById(request.getId());
        request.update(request, course.get());
        return courseRepository.save(course.get());

    }

    public Course update(Long id, RecordStatus status) {
        Optional<Course> course = findById(id);
        course.get().setRecordStatus(status);
        return courseRepository.save(course.get());
    }

    public Optional<Course> findCourseById(Long id) {
        Optional<Course> course = courseRepository.findCourseById(id);
        if (course.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course was not found for parameter {id = %s}", id));
        }
        return course;
    }

    public Optional<Course> findById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Course was not found for parameter {id = %s}", id));
        }
        return course;
    }

    public Optional<Course> findByCourseTitle(String courseTitle) {
        return courseRepository.findByCourseTitle(courseTitle);
    }

    public Map<String, Object> searchCourse(String courseCode, String courseTitle,
                                            Integer page, Integer size, String sortBy) {
        ServiceHelper<Course> helper = new ServiceHelper<>(Course.class);
        return helper.getList(
                courseRepository.searchCourse(courseCode, courseTitle,
                        helper.getPageable(sortBy, page, size)),
                page,
                size
        );
    }
}
