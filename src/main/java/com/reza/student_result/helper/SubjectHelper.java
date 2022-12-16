package com.reza.student_result.helper;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.enums.RecordStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectHelper {
    public void setBaseData(Subject subject, RecordStatus status, Boolean forUpdate) {
        subject.setRecordStatus(status);
    }
}
