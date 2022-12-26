package com.reza.student_result.services.impl;

import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.helper.StudentHelper;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.services.StudentService;
import com.reza.student_result.utils.ServiceHelper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl extends StudentService {

    private final StudentHelper studentHelper;
    public StudentServiceImpl(StudentRepository studentRepository, StudentHelper studentHelper) {
        super(studentRepository);
        this.studentHelper = studentHelper;
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
        Optional<Student> student = findStudentById(studentRequest.getId());
        studentRequest.update(studentRequest, student.get());
        return studentRepository.save(student.get());

    }
    @Override
    @Transactional
    public Student update(Long id, RecordStatus status) {
        Optional<Student> student = findStudentById(id);
        studentHelper.setBaseData(student.get(), status, true);
        return studentRepository.save(student.get());
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
    @Override
    public Optional<Student> findStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameters {id=%s}", id));
        }
        return student;
    }

    public Map<String, Object> searchStudent(Long studentRoll, String studentName, String studentEmail,
                                             String studentResult, Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.searchStudentInDB(studentRoll, studentName, studentEmail, studentResult,
                        helper.getPageable(sortBy,page,size)),
                page,
                size
        );
    }
    @Override
    public Optional<Student> findByStudentRoll(Long studentRoll) {
        return  studentRepository.findByStudentRoll(studentRoll);
    }
}
