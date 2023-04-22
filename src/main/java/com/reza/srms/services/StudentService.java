package com.reza.srms.services;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
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

    public Optional<Student> findById(Long id) {
        return studentRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED);
    }
    @Transactional
    public Student update(StudentDto dto, Student student) {

        dto.update(dto, student);

        return studentRepository.save(student);

    }

    public Student update(Long id, RecordStatus status) {
        Optional<Student> student = findById(id);
        student.get().setRecordStatus(status);
        return studentRepository.save(student.get());
    }

    public Map<String, Object> search(Long roll, String name, String email, Integer passingYear, SemesterStatus semesterStatus, Float cgpa, Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.searchStudent(roll, name, email, passingYear, semesterStatus, cgpa,
                        helper.getPageable(sortBy, page, size)),
                page,
                size
        );
    }

    @Transactional
    public Student saveResults(Student iitStudent) {
        return studentRepository.save(iitStudent);
    }

    public Student updateRecordStatus(Student student, RecordStatus status) {
        student.setRecordStatus(status);
        return studentRepository.save(student);
    }
}
