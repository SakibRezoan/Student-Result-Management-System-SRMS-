package com.reza.student_result.services.impl;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.repositories.IIT_StudentRepository;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.services.IIT_StudentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class IIT_StudentServiceImpl extends IIT_StudentService {

    public IIT_StudentServiceImpl(IIT_StudentRepository iitStudentRepository) {
        super(iitStudentRepository);
    }

    @Override
    @Transactional
    public IIT_Student save(IIT_StudentRequest iitStudentRequest) {
        IIT_Student iitStudent = iitStudentRequest.to(iitStudentRequest);
        return iitStudentRepository.save(iitStudent);
    }
    @Override
    public Optional<IIT_Student> findByRoll(Long roll) {
        return iitStudentRepository.findByRoll(roll);
    }
}
