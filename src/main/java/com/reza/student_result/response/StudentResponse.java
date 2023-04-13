package com.reza.student_result.response;

import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private Long roll;
    private String name;
    private String studentEmail;
    private Integer passingYear;
    private SemesterStatus semesterStatus;
    private Double cgpa;

    public static StudentResponse from(Student IITStudent) {
        StudentResponse IITStudentResponse = new StudentResponse();
        BeanUtils.copyProperties(IITStudent, IITStudentResponse);
        return IITStudentResponse;
    }

}
