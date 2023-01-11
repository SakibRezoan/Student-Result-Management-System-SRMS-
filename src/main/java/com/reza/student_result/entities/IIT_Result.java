package com.reza.student_result.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IIT_RESULT")
public class IIT_Result implements Serializable {
    @EmbeddedId
    @Column(name = "IIT_RESULT_ID", nullable = false)
    private IIT_Result_PK iitResultId;

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