package com.reza.student_result.validators;
import com.reza.student_result.entities.Student;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.services.impl.StudentServiceImpl;
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

    private final StudentServiceImpl studentServiceImpl;
    private final StudentRepository studentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRequest studentRequest = (StudentRequest) target;

        if (isNotEmpty(studentRequest.getStudentRoll())) {
            Optional<Student> student = studentServiceImpl.findByStudentRoll(studentRequest.getStudentRoll());
            if (student.isPresent()) {
                errors.rejectValue("studentRoll", "500", ALREADY_EXIST);
            }
        }
        if (isNotEmpty(studentRequest.getId())) {
            Optional<Student> student = studentRepository.findById(studentRequest.getId());
            if (student.isPresent()) {
                errors.rejectValue("id", "500", ALREADY_EXIST);
            }
        }

    }
}
