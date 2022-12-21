package com.reza.student_result.requests;

import com.reza.student_result.entities.Student;
import com.reza.student_result.entities.Subject;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class StudentRequest {

    private Long id;
    @NotNull
    private Long studentRoll;
    @NotBlank
    private String studentName;
    @Email
    private String studentEmail;
    @NotNull
    private String studentResult;

    public void update(StudentRequest request, Student student) {
        student.setId(request.getId());
        student.setStudentName(request.getStudentName());
        student.setStudentRoll(request.getStudentRoll());
        student.setStudentEmail(request.getStudentEmail());
        student.setStudentResult(request.getStudentResult());
//        student.setEnclosures(student.getEnclosures());
    }
    public Student to(StudentRequest studentRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentRequest, student);
        return student;
    }

}
