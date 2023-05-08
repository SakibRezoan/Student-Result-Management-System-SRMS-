package com.reza.srms.responses;

import com.reza.srms.entities.CourseWiseResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseWiseResultResponse implements Serializable {

    private Long id;
    private Long courseId;

    private Float obtainedMarks;

    private Float gpa;

    private String grade;

    private Long studentId;

    public static CourseWiseResultResponse makeResponse(CourseWiseResult courseWiseResult) {
        CourseWiseResultResponse response = new CourseWiseResultResponse();
        response.setId(courseWiseResult.getId());
        response.setCourseId(courseWiseResult.getCourse().getId());
        response.setObtainedMarks(courseWiseResult.getObtainedMarks());
        response.setGpa(courseWiseResult.getGpa());
        response.setGrade(courseWiseResult.getGrade());
        response.setStudentId(courseWiseResult.getStudent().getId());
        return response;
    }
}
