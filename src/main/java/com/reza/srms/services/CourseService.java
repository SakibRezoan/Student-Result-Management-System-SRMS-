package com.reza.srms.services;

import com.reza.srms.dtos.CourseDto;
import com.reza.srms.entities.Course;
import com.reza.srms.repositories.CourseRepository;
import com.reza.srms.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
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

    @Transactional
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    public Map<String, Object> getList(String search, Integer page, Integer size) {
        ServiceHelper<Course> helper = new ServiceHelper<>(Course.class);
        return helper.getList(
                courseRepository.getList(search,
                        helper.getPageable("", page, size)),
                page,
                size
        );
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }
}
