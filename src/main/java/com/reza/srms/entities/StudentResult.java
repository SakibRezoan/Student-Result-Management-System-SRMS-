package com.reza.srms.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "student_result")
@EqualsAndHashCode(callSuper = false, exclude = "courseWiseResult")
public class StudentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_result_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @Column(name = "total_marks_in_theory_exam", nullable = false)
    private Integer totalMarksInTheoryExam;

    @Column(name = "total_marks_in_lab_exam", nullable = false)
    private Integer totalMarksInLabExam;

    @Column(name = "obtained_marks_in_theory_exam", nullable = false)
    private Float obtainedMarksInTheoryExam;

    @Column(name = "obtained_marks_in_lab_exam", nullable = false)
    private Float obtainedMarksInLabExam;

    @Column(name = "total_obtained_marks_in_scale_100", nullable = false)
    private Float obtainedMarksInScale100;

    @Column(name = "gpa", precision = 2)
    private Float gpa;

    @Column(name = "grade", length = 2)
    private String grade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_wise_result_id")
    private CourseWiseResult courseWiseResult;
}
