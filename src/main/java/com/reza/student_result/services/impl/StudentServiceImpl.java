package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Student;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.services.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl extends StudentService {
    public StudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository);
    }

    @Override
    @Transactional
    public Student save(StudentRequest studentRequest) {
        Student student = studentRequest.to(studentRequest);
        return studentRepository.save(student);
    }
    @Override
    @Transactional
    public Student update(StudentRequest studentRequest) {
        Student student = findStudentById(studentRequest.getId());
        studentRequest.update(studentRequest, student);
        return studentRepository.save(student);

    }
    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student saveEnclosure(Student student) {
        return studentRepository.save(student);
    }
    public Student findStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameters {id=%s}", id));
        }
        return student.get();
    }
    @Override
    public List<Student> getStudentByName(String studentName) {
        return studentRepository.findByStudentName(studentName);
    }
}
