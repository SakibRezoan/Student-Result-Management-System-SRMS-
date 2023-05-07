package com.reza.srms.dtos;

import com.reza.srms.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto implements Serializable {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;

    public void update(Course course) {
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setNoOfCredits(noOfCredits);
    }

    public Course toEntity() {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setNoOfCredits(noOfCredits);
        return course;
    }

    public void toEntity(Course course) {
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setNoOfCredits(noOfCredits);
    }
}
