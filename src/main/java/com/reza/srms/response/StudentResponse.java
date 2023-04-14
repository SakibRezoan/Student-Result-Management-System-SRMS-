package com.reza.srms.response;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
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
