package com.reza.student_result.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serializable;
@Data
@AllArgsConstructor (staticName = "build")
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table (name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "student_roll", nullable = false, unique = true)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long roll;

    @Column(name = "student_name", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;


    @Column(name = "student_email", unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @Column(name = "student_result")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String result;

}
