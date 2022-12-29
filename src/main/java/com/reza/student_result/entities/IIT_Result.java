package com.reza.student_result.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSE_WISE_RESULT")
public class IIT_Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_WISE_RESULT_ID", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @Column(name = "MARKS_OBTAINED")
    private Integer marksObtained;

    @Column(name = "LETTER_GRADE")
    private String letterGrade;
    @Column(name = "GRADE_POINT")
    private Double gradePoint;

}