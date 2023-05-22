package com.reza.srms.validators;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.reza.srms.constant.ValidatorConstants.ALREADY_EXIST;

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

        Optional<Student> studentByRoll = studentService.findByRoll(studentDto.getRoll());
        if (studentByRoll.isPresent()) {
            errors.rejectValue("roll", "400", ALREADY_EXIST);
        }
        Optional<Student> studentByEmail = studentService.findByEmail(studentDto.getEmail());
        if (studentByEmail.isPresent()) {
            errors.rejectValue("email", "400", ALREADY_EXIST);
        }
        Optional<Student> studentByMobile = studentService.findByMobile(studentDto.getMobile());
        if (studentByMobile.isPresent()) {
            errors.rejectValue("mobile", "400", ALREADY_EXIST);
        }

    }
}
