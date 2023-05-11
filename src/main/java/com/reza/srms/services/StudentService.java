package com.reza.srms.services;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.repositories.StudentRepository;
import com.reza.srms.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Student save(StudentDto dto) {
        Student student = dto.toEntity();
        return studentRepository.save(student);
    }

    public Optional<Student> findByRoll(Long roll) {
        return studentRepository.findByRoll(roll);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student update(StudentDto dto, Student student) {

        dto.update(student);

        return studentRepository.save(student);

    }

    public Student changeSemesterStatus(Student student, SemesterStatus status) {
        student.setSemesterStatus(status);
        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Map<String, Object> getList(SemesterStatus semesterStatus, Long roll, String search, Integer page, Integer size) {
        ServiceHelper<Student> helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.getList(semesterStatus, roll, search.trim(),
                        helper.getPageable("", page, size)),
                page,
                size
        );
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public List<Student> findByBatchAndSemester(Integer batchNo, SemesterStatus semester) {
        return studentRepository.findByBatchAndSemesterStatus(batchNo, semester);
    }
}
