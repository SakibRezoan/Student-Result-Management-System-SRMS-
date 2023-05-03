package com.reza.srms.responses;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.SemesterStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;
    private SemesterStatus semesterStatus;

    public static CourseResponse makeResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseTitle(course.getCourseTitle());
        response.setSemesterStatus(course.getSemesterStatus());
        return response;
    }
}
