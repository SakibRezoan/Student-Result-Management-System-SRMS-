package com.reza.student_result.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "student_id", nullable = false)
    private Long id;

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    List<Enclosure> enclosures;

    public void addEnclosures(List<Enclosure> enclosure) {
        if (this.enclosures == null) {
            this.enclosures = new ArrayList<>();
        }
        this.enclosures.addAll(enclosure);
    }

}
