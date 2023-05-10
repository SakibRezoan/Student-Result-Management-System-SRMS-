package com.reza.srms.entities;

import com.reza.srms.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Long id;

    @Column(name = "student_roll", nullable = false)
    private String roll;

    @Column(name = "student_name", length = 50, nullable = false)
    private String name;

    @Column(name = "student_name_bn", columnDefinition = "nvarchar(50)", nullable = false)
    private String nameBn;

    @Column(name = "student_email")
    private String email;

    @Column(name = "student_mobile")
    private String mobile;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_status")
    private SemesterStatus semesterStatus;

    @Column(name = "cgpa")
    private Double cgpa;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
    private List<CourseWiseResult> courseWiseResultList;

    public void addCourseWiseResults(List<CourseWiseResult> courseWiseResultList) {
        if (this.courseWiseResultList == null) {
            this.courseWiseResultList = new ArrayList<>();
        }
        this.courseWiseResultList.addAll(courseWiseResultList);
    }
}
