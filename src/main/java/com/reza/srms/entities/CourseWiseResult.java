package com.reza.srms.entities;

import javax.persistence.*;

@Entity
public class CourseWiseResult extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_WISE_RESULT_ID", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @Column(name = "OBTAINED_MARKS", nullable = false)
    private Float obtainedMarks;
    @Column(name = "GPA")
    private Float gpa;

}
