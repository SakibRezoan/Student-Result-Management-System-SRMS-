package com.reza.student_result.entities;

import com.reza.student_result.enums.SemesterStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID", nullable = false)
    private Long id;

    @Column(name = "STUDENT_ROLL", nullable = false)
    private Integer roll;
    @Column(name = "STUDENT_NAME", nullable = false)
    private String name;
    @Column(name = "STUDENT_EMAIL")
    private String studentEmail;
    @Column(name = "PASSING_YEAR")
    private Integer passingYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEMESTER_STATUS")
    private SemesterStatus semesterStatus;
    @Column(name = "CGPA")
    private Double cgpa;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseWiseResult> courseWiseResultList;


    public void addCourseWiseResults(List<CourseWiseResult> courseWiseResultList) {
        if (this.courseWiseResultList == null) {
            this.courseWiseResultList = new ArrayList<>();
        }
        this.courseWiseResultList.addAll(courseWiseResultList);
    }
}
