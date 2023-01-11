package com.reza.student_result.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class IIT_Result_PK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "IIT_STUDENT_ID")
    private Long iitStudentId;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course courseId;

    public IIT_Result_PK(Long iitStudentId, Course courseId) {
        this.iitStudentId = iitStudentId;
        this.courseId = courseId;
    }

}
