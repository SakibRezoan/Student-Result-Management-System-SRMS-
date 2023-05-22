package com.reza.srms.services;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.Semester;
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

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email.trim());
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student update(StudentDto dto, Student student) {

        dto.update(student);

        return studentRepository.save(student);

    }

    public Student changeSemesterStatus(Student student, Semester status) {
        student.setSemester(status);
        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Map<String, Object> getList(Semester semester, Long roll, String search, Integer page, Integer size) {
        ServiceHelper<Student> helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.getList(semester.getLabel(), roll, search.trim(),
                        helper.getPageable("", page, size)),
                page,
                size
        );
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findByNewRollExceptExisting(Long newRoll, Long previousRoll) {
        return studentRepository.findByNewRollExceptExisting(newRoll, previousRoll);
    }

    public Optional<Student> findByNewEmailExceptExisting(String newEmail, String previousEmail) {
        return studentRepository.findByNewEmailExceptExisting(newEmail.trim().toLowerCase(), previousEmail);
    }

    public Optional<Student> findByNewMobileExceptExisting(String newMobile, String previousMobile) {
        return studentRepository.findByNewMobileExceptExisting(newMobile.trim(), previousMobile);
    }

    public Optional<Student> findByMobile(String mobile) {
        return studentRepository.findByMobile(mobile.trim());
    }
}
