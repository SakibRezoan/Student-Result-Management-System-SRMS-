package com.reza.student_result.services.impl;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.StudentStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.repositories.IIT_StudentRepository;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.services.IIT_StudentService;
import com.reza.student_result.utils.ServiceHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class IIT_StudentServiceImpl extends IIT_StudentService {

    public IIT_StudentServiceImpl(IIT_StudentRepository iitStudentRepository) {
        super(iitStudentRepository);
    }

    @Override
    @Transactional
    public IIT_Student save(IIT_StudentRequest iitStudentRequest) {
        IIT_Student iitStudent = iitStudentRequest.to(iitStudentRequest);
        return iitStudentRepository.save(iitStudent);
    }
    @Override
    public Optional<IIT_Student> findByRoll(Long roll) {
        return iitStudentRepository.findByRoll(roll);
    }
    @Override
    public Optional<IIT_Student> findStudentById(Long id) {
        Optional<IIT_Student> iitStudent = iitStudentRepository.findStudentById(id);
        if (iitStudent.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return iitStudent;
    }
    @Override
    public Optional<IIT_Student> findById(Long id) {
        Optional<IIT_Student> iitStudent = iitStudentRepository.findById(id);
        if (iitStudent.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameter {id = %s}", id));
        }
        return iitStudent;
    }
    public IIT_Student update(IIT_StudentRequest request) {
        Optional<IIT_Student> iitStudent = findStudentById(request.getId());
        request.update(request, iitStudent.get());
        return iitStudentRepository.save(iitStudent.get());

    }
    public IIT_Student update(Long id, RecordStatus status) {
        Optional<IIT_Student> iitStudent = findById(id);
        iitStudent.get().setRecordStatus(status);
        return iitStudentRepository.save(iitStudent.get());
    }
    public Map<String, Object> search(Long roll, String name, String studentEmail, Integer passingYear, StudentStatus semesterStatus, Double cgpa, Integer page, Integer size, String sortBy) {
        ServiceHelper helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                iitStudentRepository.searchIIT_StudentInDB(roll, name, studentEmail, passingYear, semesterStatus, cgpa,
                        helper.getPageable(sortBy,page,size)),
                page,
                size
        );
    }
}
