package com.reza.student_result.dtos;

import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto implements Serializable {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;
    private SemesterStatus semesterStatus;

    public void update(CourseDto courseDto, Course course) {
        BeanUtils.copyProperties(courseDto, course);
    }

    public Course toEntity(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        course.setRecordStatus(RecordStatus.ACTIVE);
        return course;
    }
}
