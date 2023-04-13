package com.reza.student_result.dtos;

import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
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

    public void update(StudentDto IITStudentRequest, Student IITStudent) {
        BeanUtils.copyProperties(IITStudentRequest, IITStudent);

    }
    public Student to(StudentDto iitStudentRequest) {
        Student iitStudent = new Student();
        BeanUtils.copyProperties(iitStudentRequest, iitStudent);
        iitStudent.setRecordStatus(RecordStatus.ACTIVE);
        return iitStudent;
    }

}
