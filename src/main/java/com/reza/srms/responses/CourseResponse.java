package com.reza.srms.responses;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.SemesterStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
@Data
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;
    private SemesterStatus semesterStatus;

    public static CourseResponse from (Course course) {
        CourseResponse courseResponse = new CourseResponse();
        BeanUtils.copyProperties(course,courseResponse);
        return courseResponse;
    }
}
