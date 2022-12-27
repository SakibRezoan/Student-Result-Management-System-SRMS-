package com.reza.student_result.validators;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.services.impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Optional;

import static com.reza.student_result.constant.ValidatorConstants.ALREADY_EXIST;
import static com.reza.student_result.utils.StringUtils.isNotEmpty;
@Component
@RequiredArgsConstructor
public class SubjectValidator implements Validator {

    private final SubjectServiceImpl subjectService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SubjectRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SubjectRequest request = (SubjectRequest) target;

        if (isNotEmpty(request.getSubjectName())) {
            Optional<Subject> subject = subjectService.findBySubjectName(request.getSubjectName());
            if (subject.isPresent()) {
                errors.rejectValue("subjectName", "500", ALREADY_EXIST);
            }
        }

        if (isNotEmpty(request.getSubjectNameBn())) {
            Optional<Subject> subject = subjectService.findBySubjectNameBn(request.getSubjectNameBn());
            if (subject.isPresent()) {
                errors.rejectValue("subjectNameBn", "500", ALREADY_EXIST);
            }
        }


    }
}
