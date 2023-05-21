package com.reza.srms.dtos;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.SemesterStatus;
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

    private SemesterStatus semester;
    @NotNull
    private Integer noOfCreditsInTheory;
    @NotNull
    private Integer noOfCreditsInLab;

    public void update(Course course) {
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setSemester(semester.getLabel());
        course.setTotalCredits(noOfCreditsInTheory + noOfCreditsInLab);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
    }

    public Course toEntity() {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setSemester(semester.getLabel());
        course.setTotalCredits(noOfCreditsInTheory + noOfCreditsInLab);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
        return course;
    }
}
