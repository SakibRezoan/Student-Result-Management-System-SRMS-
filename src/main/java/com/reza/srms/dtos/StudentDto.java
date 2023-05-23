package com.reza.srms.dtos;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull
    @Pattern(regexp = "^bsse\\d+@iit\\.du\\.ac\\.bd$", message = "email must be in format like: bsse****@iit.du.ac.bd")
    private String email;

    @Pattern(regexp = "^(01\\d{9})$", message = "mobile number must be 11 digits stated with 01")
    private String mobile;

    @NotNull
    private Integer batch;

    public Student toEntity() {
        Student student = new Student();
        student.setRoll(roll);
        student.setName(name);
        student.setNameBn(nameBn);
        student.setEmail(email);
        student.setMobile(mobile);
        student.setBatch(batch);
        student.setSemester(Semester.FIRST_SEMESTER);
        return student;
    }
    public void update(Student student) {
        student.setRoll(roll);
        student.setName(name);
        student.setNameBn(nameBn);
        student.setEmail(email);
        student.setMobile(mobile);
        student.setBatch(batch);
    }
}
