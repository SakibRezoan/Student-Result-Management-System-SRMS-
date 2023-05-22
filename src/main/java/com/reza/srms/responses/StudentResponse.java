package com.reza.srms.responses;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Semester semester;

    public static StudentResponse makeResponse(Student student) {
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


}
