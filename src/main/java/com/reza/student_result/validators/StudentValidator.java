package com.reza.student_result.validators;
import com.reza.student_result.entities.Student;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.services.StudentService;
import com.reza.student_result.services.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {

    private final StudentServiceImpl studentServiceImpl;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRequest studentRequest = (StudentRequest) target;
        if(studentRequest.getStudentName() != null){
            List<Student> student = studentServiceImpl.getStudentByName(studentRequest.getStudentName());
            if(!student.isEmpty()){
                errors.rejectValue("name", "" , "Student Already Exists");
            }
        }

    }
}
