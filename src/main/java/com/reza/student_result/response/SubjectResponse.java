package com.reza.student_result.response;

import com.reza.student_result.entities.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectResponse {
    private Long id;
    private String SubjectName;

    private String subjectNameBn;

    private String subjectCode;

    private Long subjectTypeId;

    public static SubjectResponse from(Subject subject) {
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setSubjectName(subject.getSubjectName());
        response.setSubjectNameBn(subject.getSubjectNameBn());
        response.setSubjectCode(subject.getSubjectCode());
        return response;
    }
}
