package com.reza.student_result.services;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.repositories.SubjectRepository;
import com.reza.student_result.requests.SubjectRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class SubjectService {
    protected final SubjectRepository subjectRepository;

    protected abstract Subject save(SubjectRequest request);

    protected abstract Subject update(SubjectRequest request);
    protected abstract Optional<Subject> findBySubjectName(String subjectName);

    protected abstract Optional<Subject> findById(Long id);

    protected abstract Subject update(Long id, RecordStatus status);

    public Subject findSubjectById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Subject was not found for parameters {id=%s}", id));
        }
        return subject.get();
    }


}
