package com.reza.srms.responses;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private Long roll;
    private String name;
    private String nameBn;
    private String email;
    private String mobile;
    private Integer batch;
    private Integer passingYear;
    private SemesterStatus semesterStatus;
    private Double cgpa;
    private List<CourseWiseResultResponse> courseWiseResultList;

    public static StudentResponse makeResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRoll(student.getRoll());
        response.setName(student.getName());
        response.setNameBn(student.getNameBn());
        response.setEmail(student.getEmail());
        response.setMobile(student.getMobile());
        response.setPassingYear(student.getPassingYear());
        response.setBatch(student.getBatch());
        response.setSemesterStatus(student.getSemesterStatus());
        response.setCgpa(student.getCgpa());

        return response;

    }


}
