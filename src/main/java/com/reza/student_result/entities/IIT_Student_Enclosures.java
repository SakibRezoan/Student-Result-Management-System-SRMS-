package com.reza.student_result.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IIT_STUDENT_ENCLOSURES")
public class IIT_Student_Enclosures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCLOSURE_ID")
    private Long id;
    @Column(name = "ENCLOSURE_NAME")
    private String name;

    @Column(name = "ENCLOSURE_URL")
    private String url;
}
