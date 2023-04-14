package com.reza.srms.entities;

import com.reza.srms.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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