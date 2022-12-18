package com.reza.student_result.services;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.repositories.SubjectRepository;
import com.reza.student_result.requests.SubjectRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class SubjectService {
    protected final SubjectRepository subjectRepository;

    protected abstract Subject save(SubjectRequest request);

    protected abstract Optional<Subject> findBySubjectName(String subjectName);
}
