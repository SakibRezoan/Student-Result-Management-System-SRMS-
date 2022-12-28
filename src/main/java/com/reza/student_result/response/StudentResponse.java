package com.reza.student_result.response;

import com.reza.student_result.entities.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private Long studentRoll;
    private String studentName;
    private String studentEmail;
    private String studentResult;

    public static StudentResponse from(Student student) {
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(student,response);
        return response;
    }
}
