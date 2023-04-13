package com.reza.student_result.services.implementations;

import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.SemesterStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.repositories.StudentRepository;
import com.reza.student_result.dtos.StudentDto;
import com.reza.student_result.services.StudentService;
import com.reza.student_result.utils.ServiceHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImplementation extends StudentService {

    public StudentServiceImplementation(StudentRepository iitStudentRepository) {
        super(iitStudentRepository);
    }

    @Override
    @Transactional
    public Student save(StudentDto iitStudentRequest) {
        Student iitStudent = iitStudentRequest.to(iitStudentRequest);
        return iitStudentRepository.save(iitStudent);
    }
    @Override
    public Optional<Student> findByRoll(Integer roll) {
        return iitStudentRepository.findByRoll(roll);
    }
    @Override
    public Optional<Student> findStudentById(Long id) {
        Optional<Student> iitStudent = iitStudentRepository.findStudentById(id);
        if (iitStudent.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return iitStudent;
    }
    @Override
    public Optional<Student> findById(Long id) {
        Optional<Student> iitStudent = iitStudentRepository.findById(id);
        if (iitStudent.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return iitStudent;
    }
    public Student update(StudentDto request) {
        Optional<Student> iitStudent = findStudentById(request.getId());
        request.update(request, iitStudent.get());
        return iitStudentRepository.save(iitStudent.get());

    }
    public Student update(Long id, RecordStatus status) {
        Optional<Student> iitStudent = findById(id);
        iitStudent.get().setRecordStatus(status);
        return iitStudentRepository.save(iitStudent.get());
    }
    public Map<String, Object> search(Long roll, String name, String studentEmail, Integer passingYear, SemesterStatus semesterStatus, Double cgpa, Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                iitStudentRepository.searchIIT_StudentInDB(roll, name, studentEmail, passingYear, semesterStatus, cgpa,
                        helper.getPageable(sortBy,page,size)),
                page,
                size
        );
    }
    @Transactional
    public Student saveResults(Student iitStudent) {
        return iitStudentRepository.save(iitStudent);
    }
}
