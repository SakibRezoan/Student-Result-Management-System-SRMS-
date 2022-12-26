package com.reza.student_result.response;

import com.reza.student_result.entities.CourseResult;
import com.reza.student_result.entities.IitStudent;
import com.reza.student_result.enums.Semester;
import com.reza.student_result.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IitStudentResponse {
    private Long id;

    private Long roll;

    private Semester currentSemester;
    private Date passingYear;

    private StudentStatus currentStatus;
    private Double cgpa;

    public static IitStudentResponse from(IitStudent iitStudent) {
        IitStudentResponse iitStudentResponse = new IitStudentResponse();
        BeanUtils.copyProperties(iitStudent, iitStudentResponse);
        return iitStudentResponse;
    }

}
