package com.reza.student_result.requests;

import com.reza.student_result.entities.Student;
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

    public static StudentRequest response(Student student) {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setId(student.getId());
        studentRequest.setStudentRoll(student.getStudentRoll());
        studentRequest.setStudentName(student.getStudentName());
        studentRequest.setStudentEmail(student.getStudentEmail());
        studentRequest.setStudentResult(student.getStudentResult());
        return studentRequest;
    }

    public Student to(StudentRequest studentRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentRequest, student);
        return student;
    }

}
