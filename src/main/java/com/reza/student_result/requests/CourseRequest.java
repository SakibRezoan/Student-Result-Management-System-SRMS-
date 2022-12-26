package com.reza.student_result.requests;

import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Integer noOfCredits;
    private Semester semesterNo;

    public void update(CourseRequest courseRequest, Course course) {
        BeanUtils.copyProperties(courseRequest, course);
    }
    public Course to(CourseRequest courseRequest) {
        Course course = new Course();
        BeanUtils.copyProperties(courseRequest, course);
        course.setRecordStatus(RecordStatus.ACTIVE);
        return course;
    }
}
