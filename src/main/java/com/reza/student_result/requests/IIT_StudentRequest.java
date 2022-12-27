package com.reza.student_result.requests;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IIT_StudentRequest {
    private Long id;
    @NotNull
    private Long roll;
    @NotNull
    private String name;
    private String passingYear;
    private StudentStatus currentStatus;
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
