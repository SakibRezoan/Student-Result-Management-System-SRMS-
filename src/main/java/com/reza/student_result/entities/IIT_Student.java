package com.reza.student_result.entities;

import com.reza.student_result.enums.StudentStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Column(name = "STUDENT_NAME", nullable = false)
    private String name;
    @Column(name = "PASSING_YEAR", nullable = true)
    private String passingYear;
    @Column(name = "CURRENT_STATUS")
    private StudentStatus currentStatus;
    @Column(name = "CGPA", nullable = true)
    private Double cgpa;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "IIT_STUDENT_ID")
    private List<CourseResult> courseResults;

}
