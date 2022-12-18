package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.helper.SubjectHelper;
import com.reza.student_result.repositories.SubjectRepository;
import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.services.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectServiceImpl extends SubjectService {

    private final SubjectHelper subjectHelper;


    public SubjectServiceImpl(SubjectHelper subjectHelper, SubjectRepository subjectRepository) {
        super(subjectRepository);
        this.subjectHelper = subjectHelper;
    }

    @Override
    @Transactional
    public Subject save(SubjectRequest request) {
        Subject subject = request.to(request);
        return subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName);
    }
}
