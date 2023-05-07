package com.reza.srms.validators;

import com.reza.srms.entities.Course;
import com.reza.srms.dtos.CourseDto;
import com.reza.srms.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.reza.srms.constant.ValidatorConstants.ALREADY_EXIST;
import static com.reza.srms.utils.StringUtils.isNotEmpty;
@Component
@RequiredArgsConstructor
public class CourseValidator implements Validator {

    private final CourseService courseService;
    @Override
    public boolean supports(Class<?> clazz) {
        return CourseDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;

        if (isNotEmpty(courseDto.getCourseCode())) {
            Optional<Course> course = courseService.findByCourseCode(courseDto.getCourseCode());
            if (course.isPresent()) {
                errors.rejectValue("courseCode", "500", ALREADY_EXIST);
            }
        }
        if (isNotEmpty(courseDto.getCourseTitle())) {
            Optional<Course> course = courseService.findByCourseTitle(courseDto.getCourseTitle());
            if (course.isPresent()) {
                errors.rejectValue("courseTitle", "500", ALREADY_EXIST);
            }
        }

    }
}
