package com.reza.srms.responses;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Objects.nonNull;

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
    private String session;
    private Integer passingYear;
    private Semester semester;
    private Double cgpa;
    private List<SemesterWiseResultResponse> semesterWiseResultList;

    public static StudentResponse makeCommonResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRoll(student.getRoll());
        response.setName(student.getName());
        response.setNameBn(student.getNameBn());
        response.setEmail(student.getEmail());
        response.setMobile(student.getMobile());
        response.setBatch(student.getBatch());
        response.setSemester(student.getSemester());

        return response;

    }


    public static StudentResponse makeDetailResponse(Student student) {
        StudentResponse response = makeCommonResponse(student);
        response.setPassingYear(student.getPassingYear());
        response.setSession(student.getSession());
        response.setCgpa(student.getCgpa());
        if (nonNull(student.getSemesterWiseResultList()))
            response.setSemesterWiseResultList(student.getSemesterWiseResultList().stream().map(SemesterWiseResultResponse::makeResponse).toList());
        return response;
    }
}
