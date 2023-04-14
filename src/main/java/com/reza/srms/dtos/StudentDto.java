package com.reza.srms.dtos;

import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {
    private Long id;
    @NotNull
    private Integer roll;
    @NotNull
    private String name;
    @Email
    private String studentEmail;
    private Integer passingYear;
    private SemesterStatus semesterStatus;
    private Double cgpa;

    public void update(StudentDto dto, Student student) {
        BeanUtils.copyProperties(dto, student);
    }

    public Student toEntity(StudentDto dto) {
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        student.setRecordStatus(RecordStatus.ACTIVE);
        return student;
    }
}
