package com.reza.srms.entities;

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
@Table(name = "course_wise_result")
public class CourseWiseResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_wise_result_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @Column(name = "result_upload_file_name")
    private String fileName;

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
    @JoinColumn(name = "semester_wise_result_id")
    private SemesterWiseResult semesterWiseResult;

}
