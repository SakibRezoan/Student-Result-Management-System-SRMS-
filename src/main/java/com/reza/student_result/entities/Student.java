package com.reza.student_result.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@ToString
@Table (name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_roll", unique = true)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long studentRoll;

    @Column(name = "student_name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String studentName;


    @Column(name = "student_email", unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String studentEmail;

    @Column(name = "student_result")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String studentResult;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<Enclosure> enclosures;

    public void addEnclosures(List<Enclosure> enclosure) {
        if (this.enclosures == null) {
            this.enclosures = new ArrayList<>();
        }
        this.enclosures.addAll(enclosure);
    }

}
