package com.reza.srms.services;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Optional<Student> findByRoll(String roll) {
        return studentRepository.findByRoll(roll.trim());
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student update(StudentDto dto, Student student) {

        dto.update(student);

        return studentRepository.save(student);

    }

//    public Map<String, Object> search(Long roll, String name, String email, Integer passingYear, SemesterStatus semesterStatus, Float cgpa, Integer page, Integer size, String sortBy) {
//        ServiceHelper helper = new ServiceHelper<>(Student.class);
//        return helper.getList(
//                studentRepository.searchStudent(roll, name, email, passingYear, semesterStatus, cgpa,
//                        helper.getPageable(sortBy, page, size)),
//                page,
//                size
//        );
//    }

    public Student changeSemesterStatus(Student student, SemesterStatus status) {
        student.setSemesterStatus(status);
        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }
}
