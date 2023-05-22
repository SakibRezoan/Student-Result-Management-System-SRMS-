package com.reza.srms.entities;

import com.reza.srms.enums.CourseType;
import com.reza.srms.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long id;

    @Column(name = "course_code", unique = true, nullable = false, length = 10)
    private String courseCode;

    @Column(name = "course_title", unique = true, nullable = false, length = 50)
    private String courseTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "no_of_credits_in_theory")
    private Integer noOfCreditsInTheory;

    @Column(name = "no_of_credits_in_lab")
    private Integer noOfCreditsInLab;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", length = 20)
    private CourseType courseType;

}