package com.reza.student_result.response;

import com.reza.student_result.entities.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SubjectResponse {
    private Long id;
    private String subjectName;
    private String subjectNameBn;
    private String subjectCode;
    private Long subjectTypeId;
    public static SubjectResponse from(Subject subject) {
        SubjectResponse response = new SubjectResponse();
        BeanUtils.copyProperties(subject, response);
        return response;
    }
}
