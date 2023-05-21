package com.reza.srms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "course_wise_result")
@EqualsAndHashCode(callSuper = false, exclude = "student")
public class CourseWiseResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_wise_result_id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @Column(name = "total_marks_in_theory_exam", nullable = false)
    private Integer totalMarksInTheoryExam;

    @Column(name = "total_marks_in_lab_exam", nullable = false)
    private Integer totalMarksInLabExam;

    @Column(name = "obtained_marks_in_theory_exam", nullable = false)
    private Float obtainedMarksInTheoryExam;

    @Column(name = "obtained_marks_in_lab_exam", nullable = false)
    private Float obtainedMarksInLabExam;

    @Column(name = "gpa", precision = 2)
    private Float gpa;

    @Column(name = "grade", length = 2)
    private String grade;
}
