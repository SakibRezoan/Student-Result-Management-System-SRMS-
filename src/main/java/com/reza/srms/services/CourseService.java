package com.reza.srms.services;

import com.reza.srms.dtos.CourseDto;
import com.reza.srms.entities.Course;
import com.reza.srms.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public Course save(CourseDto courseDto) {
        Course course = courseDto.toEntity();
        return courseRepository.save(course);
    }

    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    public Course update(Course course, CourseDto dto) {
        dto.update(course);
        return courseRepository.save(course);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> findByCourseTitle(String courseTitle) {
        return courseRepository.findByCourseTitle(courseTitle);
    }

//    public Map<String, Object> searchCourse(String courseCode, String courseTitle,
//                                            Integer page, Integer size, String sortBy) {
//        ServiceHelper<Course> helper = new ServiceHelper<>(Course.class);
//        return helper.getList(
//                courseRepository.searchCourse(courseCode, courseTitle,
//                        helper.getPageable(sortBy, page, size)),
//                page,
//                size
//        );
//    }

    @Transactional
    public void delete(Course course) {
        courseRepository.delete(course);
    }

}
