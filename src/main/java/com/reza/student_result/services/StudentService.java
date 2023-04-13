package com.reza.student_result.services;

import com.reza.student_result.entities.Student;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.dtos.StudentDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@Data
@RequiredArgsConstructor
public abstract class StudentService {

    protected final StudentRepository iitStudentRepository;
    protected abstract Student save(StudentDto iitStudentRequest);
    protected abstract Optional<Student> findByRoll(Integer roll);
    protected abstract Optional<Student> findById(Long id);
    protected abstract Optional<Student> findStudentById(Long id);
}
