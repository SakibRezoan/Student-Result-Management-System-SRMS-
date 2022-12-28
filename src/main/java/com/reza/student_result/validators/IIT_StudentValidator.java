package com.reza.student_result.validators;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.repositories.IIT_StudentRepository;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.services.impl.IIT_StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.reza.student_result.constant.ValidatorConstants.ALREADY_EXIST;
import static com.reza.student_result.utils.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class IIT_StudentValidator implements Validator {
    private final IIT_StudentServiceImpl iitStudentServiceImpl;
    private final IIT_StudentRepository iitStudentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return IIT_StudentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IIT_StudentRequest iitStudentRequest = (IIT_StudentRequest) target;

        if (isNotEmpty(iitStudentRequest.getRoll())) {
            Optional<IIT_Student> iitStudent = iitStudentServiceImpl.findByRoll(iitStudentRequest.getRoll());
            if (iitStudent.isPresent()) {
                errors.rejectValue("roll", "500", ALREADY_EXIST);
            }
        }
        if (isNotEmpty(iitStudentRequest.getId())) {
            Optional<IIT_Student> iitStudent = iitStudentRepository.findById(iitStudentRequest.getId());
            if (iitStudent.isPresent()) {
                errors.rejectValue("id", "500", ALREADY_EXIST);
            }
        }

    }
}
