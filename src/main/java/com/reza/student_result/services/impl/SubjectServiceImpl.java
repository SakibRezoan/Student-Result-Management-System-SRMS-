package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.helper.SubjectHelper;
import com.reza.student_result.repositories.SubjectRepository;
import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.services.SubjectService;
import com.reza.student_result.utils.ServiceHelper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
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
    @Transactional
    public Subject update(SubjectRequest request) {
        Subject subject = findSubjectById(request.getId());
        request.update(request, subject);
        return subjectRepository.save(subject);
    }
    @Override
    @Transactional
    public Subject update(Long id, RecordStatus status) {
        Subject subject = findSubjectById(id);
        subjectHelper.setBaseData(subject, status, true);
        return subjectRepository.save(subject);
    }
    @Override
    public Optional<Subject> findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName);
    }

    public Optional<Subject> findBySubjectNameBn(String subjectNameBn) {
        return subjectRepository.findBySubjectNameBn(subjectNameBn);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public Map<String, Object> searchSubject(String subjectName, Long subjectTypeId,
                                             Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Subject.class);
        return helper.getList(
            subjectRepository.searchSubject(subjectName, subjectTypeId,
                    helper.getPageable(sortBy,page,size)),
            page,
            size
        );

    }
}
