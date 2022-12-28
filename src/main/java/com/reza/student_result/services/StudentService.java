package com.reza.student_result.services;

import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class StudentService{

    protected final StudentRepository studentRepository;

    protected abstract Student save (StudentRequest studentRequest);

    protected abstract Student update (StudentRequest studentRequest);

    protected abstract Student update(Long id, RecordStatus status);
    protected abstract Optional<Student> findById(Long id);
    protected abstract Optional<Student> findByStudentRoll(Long studentRoll);

    public Optional<Student> findStudentById(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameters {id=%s}", id));
        }
        return student;
    }
    protected abstract Student saveEnclosure(Student student);

}
