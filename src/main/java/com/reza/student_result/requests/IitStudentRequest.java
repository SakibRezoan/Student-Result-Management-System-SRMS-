package com.reza.student_result.requests;

import com.reza.student_result.entities.IitStudent;
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
public class IitStudentRequest {
    private Long id;

    @NotBlank
    @NotBlank
    private Long roll;
    private Semester currentSemester;
    private Date passingYear;
    private StudentStatus currentStatus;
    private Double cgpa;

    public void update(IitStudentRequest iitStudentRequest, IitStudent iitStudent) {
        BeanUtils.copyProperties(iitStudentRequest,iitStudent);

    }
    public IitStudent to(IitStudentRequest iitStudentRequest) {
        IitStudent iitStudent = new IitStudent();
        BeanUtils.copyProperties(iitStudentRequest, iitStudent);
        return iitStudent;
    }

}
