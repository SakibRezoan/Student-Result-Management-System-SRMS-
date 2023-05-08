package com.reza.srms.responses;

import com.reza.srms.entities.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseResponse {
    private Long id;

    private String courseCode;

    private String courseTitle;

    private Integer noOfCredits;

    public static CourseResponse makeResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseTitle(course.getCourseTitle());
        return response;
    }
}
