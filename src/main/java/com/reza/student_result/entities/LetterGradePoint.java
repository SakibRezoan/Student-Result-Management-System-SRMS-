package com.reza.student_result.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LetterGradePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LETTER_GRADE_POINT_ID", nullable = false)
    private Long id;

    @Column(name = "LETTER_GRADE")
    private String letterGrade;
    @Column(name = "GRADE_POINT")
    private Double gradePoint;

}
