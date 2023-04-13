package com.reza.student_result.validators;

import com.reza.student_result.entities.Course;
import com.reza.student_result.dtos.CourseDto;
import com.reza.student_result.services.implementations.CourseServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.reza.student_result.constant.ValidatorConstants.ALREADY_EXIST;
import static com.reza.student_result.utils.StringUtils.isNotEmpty;
@Component
@RequiredArgsConstructor
public class CourseValidator implements Validator {
    private final CourseServiceImplementation courseServiceImplementation;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;

        if (isNotEmpty(courseDto.getCourseCode())) {
            Optional<Course> course = courseServiceImplementation.findByCourseCode(courseDto.getCourseCode());
            if (course.isPresent()) {
                errors.rejectValue("courseCode", "500", ALREADY_EXIST);
            }
        }
        if (isNotEmpty(courseDto.getCourseTitle())) {
            Optional<Course> course = courseServiceImplementation.findByCourseTitle(courseDto.getCourseTitle());
            if (course.isPresent()) {
                errors.rejectValue("courseTitle", "500", ALREADY_EXIST);
            }
        }

    }
}
