package com.reza.srms.entities;

import com.reza.srms.enums.Semester;
import com.reza.srms.enums.SemesterStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "semester_wise_result")
@EqualsAndHashCode(callSuper = false, exclude = "student")
public class SemesterWiseResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_wise_result_id", nullable = false)
    private Long id;

    @Column(name = "gpa")
    private Float cgpa;

    @Column(name = "grade", length = 2)
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_status")
    private SemesterStatus semesterStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "semesterWiseResult")
    private List<CourseWiseResult> courseWiseResultList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public void addCourseWiseResultList(List<CourseWiseResult> courseWiseResultList) {
        if (this.courseWiseResultList == null) {
            this.courseWiseResultList = new ArrayList<>();
        }
        this.courseWiseResultList.addAll(courseWiseResultList);
    }
}
