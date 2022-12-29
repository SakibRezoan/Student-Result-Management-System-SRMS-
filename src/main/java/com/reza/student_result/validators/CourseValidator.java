package com.reza.student_result.validators;

import com.reza.student_result.entities.Course;
import com.reza.student_result.requests.CourseRequest;
import com.reza.student_result.services.impl.CourseServiceImpl;
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
    private final CourseServiceImpl courseServiceImpl;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest courseRequest = (CourseRequest) target;

        if (isNotEmpty(courseRequest.getCourseCode())) {
            Optional<Course> course = courseServiceImpl.findByCourseCode(courseRequest.getCourseCode());
            if (course.isPresent()) {
                errors.rejectValue("courseCode", "500", ALREADY_EXIST);
            }
        }
        if (isNotEmpty(courseRequest.getCourseTitle())) {
            Optional<Course> course = courseServiceImpl.findByCourseTitle(courseRequest.getCourseTitle());
            if (course.isPresent()) {
                errors.rejectValue("courseTitle", "500", ALREADY_EXIST);
            }
        }

    }
}
