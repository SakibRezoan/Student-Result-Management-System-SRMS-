package com.reza.student_result.validators;

import com.reza.student_result.dtos.StudentDto;
import com.reza.student_result.entities.Student;
import com.reza.student_result.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.reza.student_result.constant.ValidatorConstants.ALREADY_EXIST;
import static com.reza.student_result.utils.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {
    private final StudentService studentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentDto studentDto = (StudentDto) target;

        if (isNotEmpty(studentDto.getRoll())) {
            Optional<Student> student = studentService.findByRoll(studentDto.getRoll());
            if (student.isPresent()) {
                errors.rejectValue("roll", "500", ALREADY_EXIST);
            }
        }

    }
}
