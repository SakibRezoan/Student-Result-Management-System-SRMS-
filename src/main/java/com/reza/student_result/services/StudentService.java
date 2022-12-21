package com.reza.student_result.services;

import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.entities.Student;
import com.reza.student_result.exceptions.StudentNotFoundException;
import com.reza.student_result.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentServiceForPagingAndSorting{
    @Autowired
    private StudentRepository studentRepository;
    @Transactional
    public Student saveNewStudent(StudentRequest studentRequest) {
        Student student = studentRequest.to(studentRequest);
        return studentRepository.save(student);
    }

    public List<Student> fetchAllStudents () throws StudentNotFoundException {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findPaginated(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Student> pagedResult = studentRepository.findAll(paging);
        return pagedResult.toList();
    }

    public List<Student> getStudentByName (String name) {
        return studentRepository.findByStudentName(name);
    }

    public Optional<Student> findById(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    public Student updateStudent (Student student) {
        return studentRepository.save(student);
    }

    public String deleteOne (int id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            return "Failed to delete the user";
        }
        return "Deleted Successfully";
    }
    public Student getStudentByEmail(String email) {
        return studentRepository.findByStudentEmail(email);
    }

    public Student getStudentByRoll(long roll) {
        return studentRepository.findByStudentRoll(roll);
    }
    public List<Student> getStudentsByResult(String result) {
        return studentRepository.findAllByStudentResult(result);
    }
    public Student saveEnclosure(Student student) {
        return studentRepository.save(student);
    }
}
