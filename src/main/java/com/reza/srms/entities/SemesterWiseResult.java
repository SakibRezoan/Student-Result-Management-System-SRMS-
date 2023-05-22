package com.reza.srms.entities;

import com.reza.srms.enums.SemesterStatus;
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
@Table(name = "student_semester_wise_result")
@EqualsAndHashCode(callSuper = false, exclude = "student")
public class SemesterWiseResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_semester_wise_result_id", nullable = false)
    private Long id;

    @Column(name = "gpa")
    private Float cgpa;

    @Column(name = "grade", length = 2)
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_semester_status")
    private SemesterStatus previousSemesterStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_semester_status")
    private SemesterStatus currentSemesterStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
