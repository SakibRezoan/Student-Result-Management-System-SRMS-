package com.reza.student_result.services;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.repositories.IIT_StudentRepository;
import com.reza.student_result.requests.IIT_StudentRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
@Data
@RequiredArgsConstructor
public abstract class IIT_StudentService {

    protected final IIT_StudentRepository iitStudentRepository;
    protected abstract IIT_Student save(IIT_StudentRequest iitStudentRequest);
    protected abstract Optional<IIT_Student> findByRoll(Long roll);
}
