package com.reza.srms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "course_code", nullable = false, length = 10)
    private String courseCode;

    @NotBlank
    @Column(name = "course_title", nullable = false, length = 50)
    private String courseTitle;

    @NotBlank
    @Column(name = "no_of_credits", nullable = false)
    private Integer noOfCredits;

}