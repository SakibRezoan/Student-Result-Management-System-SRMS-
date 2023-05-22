package com.reza.srms.entities;

import com.reza.srms.enums.Semester;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "course_wise_result")
public class CourseWiseResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_wise_result_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @Column(name = "batch_no")
    private Integer batchNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Column(name = "result_file_name")
    private String fileName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courseWiseResult")
    private List<StudentResult> studentResultList;

    public void addStudentResultList(List<StudentResult> studentResultList) {
        if (this.studentResultList == null) {
            this.studentResultList = new ArrayList<>();
        }
        this.studentResultList.addAll(studentResultList);
    }

}
