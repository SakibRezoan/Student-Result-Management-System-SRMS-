package com.reza.srms.entities;

import com.reza.srms.enums.SemesterStatus;
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
    @Column(name = "course_code", nullable = false)
    private String courseCode;
    @Column(name = "course_title", nullable = false)
    private String courseTitle;
    @Column(name = "no_of_credits", nullable = false)
    private Integer noOfCredits;
    @Column(name = "semester_status")
    private SemesterStatus semesterStatus;

}