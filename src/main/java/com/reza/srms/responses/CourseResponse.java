package com.reza.srms.responses;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.CourseType;
import com.reza.srms.enums.SemesterStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseResponse {
    private Long id;

    private String courseCode;

    private String courseTitle;

    private SemesterStatus semester;

    private Integer totalCredits;

    private Integer noOfCreditsInTheory;

    private Integer noOfCreditsInLab;

    private CourseType courseType;

    public static CourseResponse makeResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseTitle(course.getCourseTitle());
        response.setSemester(course.getSemester());
        response.setTotalCredits(course.getTotalCredits());
        response.setNoOfCreditsInTheory(course.getNoOfCreditsInTheory());
        response.setNoOfCreditsInLab(course.getNoOfCreditsInLab());
        response.setCourseType(course.getCourseType());
        return response;
    }
}
