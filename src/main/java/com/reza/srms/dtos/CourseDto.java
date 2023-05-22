package com.reza.srms.dtos;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.CourseType;
import com.reza.srms.enums.Semester;
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

    private Semester semester;

    @NotNull
    private Integer totalCredits;

    private Integer noOfCreditsInTheory;

    private Integer noOfCreditsInLab;

    private CourseType courseType;

    public Course toEntity() {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setSemester(semester);
        course.setTotalCredits(totalCredits);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
        course.setCourseType(courseType);

        return course;
    }

    public void update(Course course) {
        course.setCourseCode(courseCode);
        course.setCourseTitle(courseTitle);
        course.setSemester(semester);
        course.setTotalCredits(totalCredits);
        course.setNoOfCreditsInTheory(noOfCreditsInTheory);
        course.setNoOfCreditsInLab(noOfCreditsInLab);
        course.setCourseType(courseType);
    }
}
