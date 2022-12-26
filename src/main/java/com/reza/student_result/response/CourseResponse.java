package com.reza.student_result.response;

import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.Semester;
import org.springframework.beans.BeanUtils;

public class CourseResponse {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;
    private Semester semesterNo;

    public static CourseResponse from (Course course) {
        CourseResponse courseResponse = new CourseResponse();
        BeanUtils.copyProperties(course, courseResponse);
        return courseResponse;
    }
}
