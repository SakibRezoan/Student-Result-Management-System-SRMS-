package com.reza.student_result.requests;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.enums.Semester;
import com.reza.student_result.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IIT_StudentRequest {
    private Long id;

    @NotBlank
    @NotBlank
    private Long roll;
    private Semester currentSemester;
    private Date passingYear;
    private StudentStatus currentStatus;
    private Double cgpa;

    public void update(IIT_StudentRequest IITStudentRequest, IIT_Student IITStudent) {
        BeanUtils.copyProperties(IITStudentRequest, IITStudent);

    }
    public IIT_Student to(IIT_StudentRequest IITStudentRequest) {
        IIT_Student IITStudent = new IIT_Student();
        BeanUtils.copyProperties(IITStudentRequest, IITStudent);
        return IITStudent;
    }

}
