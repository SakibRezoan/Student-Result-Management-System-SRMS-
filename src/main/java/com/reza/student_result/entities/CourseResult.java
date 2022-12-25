package com.reza.student_result.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSE_RESULT")
public class CourseResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_RESULT_ID", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "COURSE_ID")
    private Course courseId;
    @OneToOne
    @JoinColumn(name = "LETTER_GRADE_POINT_ID")
    private LetterGradePoint letterGradePointId;

}