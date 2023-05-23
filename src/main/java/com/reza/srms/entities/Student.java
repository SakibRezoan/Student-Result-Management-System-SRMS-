package com.reza.srms.entities;

import com.reza.srms.enums.Semester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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

    @Column(name = "student_roll", unique = true, nullable = false)
    private Long roll;

    @Column(name = "student_name", length = 50, nullable = false)
    private String name;

    @Column(name = "student_name_bn", columnDefinition = "nvarchar(50)", nullable = false)
    private String nameBn;

    @Column(name = "student_email", unique = true, nullable = false)
    private String email;

    @Column(name = "student_mobile", unique = true, length = 11)
    private String mobile;

    @Column(name = "batch", nullable = false)
    private Integer batch;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false)
    private Semester semester;

    @Column(name = "cgpa")
    private Double cgpa;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
    private List<SemesterWiseResult> semesterWiseResultList;

    public void addSemesterWiseResultList(List<SemesterWiseResult> semesterWiseResultList) {
        if (this.semesterWiseResultList == null) {
            this.semesterWiseResultList = new ArrayList<>();
        }
        this.semesterWiseResultList.addAll(semesterWiseResultList);
    }

}
