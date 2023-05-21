package com.reza.srms.dtos;

import com.reza.srms.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto implements Serializable {
    private Long id;
    @NotNull
    private String courseCode;
    @NotNull
    private String courseTitle;
    @NotNull
    private Integer noOfCreditsInTheory;
    @NotNull
    private Integer noOfCreditsInLab;

    public void update(Course course) {
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setTotalCredits(noOfCreditsInTheory + noOfCreditsInLab);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
    }

    public Course toEntity() {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setTotalCredits(noOfCreditsInTheory + noOfCreditsInLab);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
        return course;
    }
}
