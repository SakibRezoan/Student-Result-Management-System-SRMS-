package com.reza.student_result.validators;

import com.reza.student_result.entities.Student;
import com.reza.student_result.dtos.StudentDto;
import com.reza.student_result.services.implementations.StudentServiceImplementation;
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
    private final StudentServiceImplementation studentServiceImplementation;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentDto dto = (StudentDto) target;

        if (isNotEmpty(dto.getRoll())) {
            Optional<Student> iitStudent = studentServiceImplementation.findByRoll(dto.getRoll());
            if (iitStudent.isPresent()) {
                errors.rejectValue("roll", "500", ALREADY_EXIST);
            }
        }

    }
}
