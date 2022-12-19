package com.reza.student_result.validators;
import com.reza.student_result.dto.StudentDto;
import com.reza.student_result.entities.Student;
import com.reza.student_result.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {

    private final StudentService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentDto dto = (StudentDto) target;
        if(!dto.getName().isEmpty()){
            List<Student> student = service.getStudentByName(dto.getName());
            if(!student.isEmpty()){
                errors.rejectValue("name", null , "Already Exists");
            }
        }

    }
}
