package com.reza.student_result.requests;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IIT_StudentRequest {
    private Long id;
    @NotNull
    private Long roll;
    @NotNull
    private String name;
    @Email
    private String studentEmail;
    private Integer passingYear;
    private StudentStatus semesterStatus;
    private Double cgpa;

    public void update(IIT_StudentRequest IITStudentRequest, IIT_Student IITStudent) {
        BeanUtils.copyProperties(IITStudentRequest, IITStudent);

    }
    public IIT_Student to(IIT_StudentRequest iitStudentRequest) {
        IIT_Student iitStudent = new IIT_Student();
        BeanUtils.copyProperties(iitStudentRequest, iitStudent);
        iitStudent.setRecordStatus(RecordStatus.ACTIVE);
        return iitStudent;
    }

}
