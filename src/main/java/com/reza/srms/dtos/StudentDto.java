package com.reza.srms.dtos;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {

    private Long id;

    @NotNull
    private Long roll;
    @NotNull
    private String name;
    private String nameBn;
    private String email;
    private String mobile;
    @NotNull
    private Integer batch;
    private SemesterStatus semesterStatus;

    public void update(Student student) {
        student.setRoll(roll);
        student.setName(name);
        student.setNameBn(nameBn);
        student.setEmail(email);
        student.setMobile(mobile);
        student.setBatch(batch);
        student.setSemesterStatus(semesterStatus);
    }

    public Student toEntity() {
        Student student = new Student();
        student.setRoll(roll);
        student.setName(name);
        student.setNameBn(nameBn);
        student.setEmail(email);
        student.setMobile(mobile);
        student.setBatch(batch);
        student.setSemesterStatus(semesterStatus);
        return student;
    }
}
