package com.reza.student_result.entities;

import com.reza.student_result.enums.SemesterStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSE")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID", nullable = false)
    private Long id;
    @Column(name = "COURSE_CODE", nullable = false)
    private String courseCode;
    @Column(name = "COURSE_TITLE", nullable = false)
    private String courseTitle;
    @Column(name = "NO_OF_CREDITS", nullable = false)
    private Integer noOfCredits;
    @Column(name = "SEMESTER_STATUS")
    private SemesterStatus semesterStatus;

}