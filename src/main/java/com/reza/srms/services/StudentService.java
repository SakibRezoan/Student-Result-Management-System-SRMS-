package com.reza.srms.services;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.exceptions.ResourceNotFoundException;
import com.reza.srms.repositories.StudentRepository;
import com.reza.srms.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Student save(StudentDto dto) {
        Student student = dto.toEntity(dto);
        return studentRepository.save(student);
    }

    public Optional<Student> findByRoll(Integer roll) {
        return studentRepository.findByRoll(roll);
    }

    public Optional<Student> findStudentById(Long id) {
        Optional<Student> student = studentRepository.findStudentById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return student;
    }

    public Optional<Student> findById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return student;
    }

    public Student update(StudentDto request) {
        Optional<Student> student = findStudentById(request.getId());
        request.update(request, student.get());
        return studentRepository.save(student.get());

    }

    public Student update(Long id, RecordStatus status) {
        Optional<Student> student = findById(id);
        student.get().setRecordStatus(status);
        return studentRepository.save(student.get());
    }

    public Map<String, Object> search(Long roll, String name, String studentEmail, Integer passingYear, SemesterStatus semesterStatus, Double cgpa, Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.searchIIT_StudentInDB(roll, name, studentEmail, passingYear, semesterStatus, cgpa,
                        helper.getPageable(sortBy, page, size)),
                page,
                size
        );
    }

    @Transactional
    public Student saveResults(Student iitStudent) {
        return studentRepository.save(iitStudent);
    }
}
