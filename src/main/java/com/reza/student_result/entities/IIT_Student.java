package com.reza.student_result.entities;

import com.reza.student_result.enums.SemesterStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Column(name = "IIT_STUDENT_ROLL", nullable = false)
    private Long roll;
    @Column(name = "IIT_STUDENT_NAME", nullable = false)
    private String name;
    @Column(name = "IIT_STUDENT_EMAIL")
    @Email
    private String studentEmail;
    @Column(name = "PASSING_YEAR", nullable = true)
    private Integer passingYear;
    @Column(name = "SEMESTER_STATUS")
    private SemesterStatus semesterStatus;
    @Column(name = "CGPA", nullable = true)
    private Double cgpa;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "IIT_STUDENT_ID")
    private List<IIT_Result> iitResults;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "IIT_STUDENT_ID")
    private List<IIT_Student_Enclosures> iitStudentEnclosures;

    public void addEnclosures(List<IIT_Student_Enclosures> iitStudentEnclosures) {
        if (this.iitStudentEnclosures == null) {
            this.iitStudentEnclosures = new ArrayList<>();
        }
        this.iitStudentEnclosures.addAll(iitStudentEnclosures);
    }


    public void addResults(List<IIT_Result> iitResults) {
        if (this.iitResults == null) {
            this.iitResults = new ArrayList<>();
        }
        this.iitResults.addAll(iitResults);
    }
}
