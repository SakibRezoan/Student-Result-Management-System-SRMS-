package com.reza.student_result.entities;

import com.reza.student_result.enums.Semester;
import com.reza.student_result.enums.StudentStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IIT_Student extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IIT_STUDENT_ID", nullable = false)
    private Long id;

    @Column(name = "STUDENT_ROLL", nullable = false)
    private Long roll;
    @Column(name = "CURRENT_SEMESTER")
    private Semester currentSemester;
    @Column(name = "PASSING_YEAR")
    private Date passingYear;
    @Column(name = "CURRENT_STATUS")
    private StudentStatus currentStatus;
    @Column(name = "CGPA")
    private Double cgpa;

    @OneToMany
    @JoinColumn(name = "IIT_STUDENT_ID")
    private List<CourseResult> courseResults;

}
