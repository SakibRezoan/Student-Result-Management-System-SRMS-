package com.reza.student_result.services;

import com.reza.student_result.dto.StudentRequest;
import com.reza.student_result.entities.Student;
import com.reza.student_result.exceptions.StudentNotFoundException;
import com.reza.student_result.repositories.StudentRepo;
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
    private StudentRepo studentRepo;

    public Student saveNewStudent (StudentRequest studentRequest) {
        Student student = Student.build(0,
                                        studentRequest.getRoll(),
                                        studentRequest.getName(),
                                        studentRequest.getEmail(),
                                        studentRequest.getResult() );
        return studentRepo.save(student);
    }
    public List<Student> fetchAllStudents () throws StudentNotFoundException {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> findPaginated(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Student> pagedResult = studentRepo.findAll(paging);
        return pagedResult.toList();
    }

    public List<Student> getStudentByName (String name) {
        return studentRepo.findByName(name);
    }

    public Student getStudentById (int id) throws StudentNotFoundException {
        Optional<Student> student = studentRepo.findById(id);
        if(student.isPresent()) {
            return student.get();
        }else {
            throw new StudentNotFoundException("Student not found with id : " + id);
        }
    }

    public Student updateStudent (Student student) {
        return studentRepo.save(student);
    }

    public String deleteOne (int id) {
        try {
            studentRepo.deleteById(id);
        } catch (Exception e) {
            return "Failed to delete the user";
        }
        return "Deleted Successfully";
    }
    public Student getStudentByEmail(String email) {
        return studentRepo.findByEmail(email);
    }

    public Student getStudentByRoll(long roll) {
        return studentRepo.findByRoll(roll);
    }
    public List<Student> getStudentsByResult(String result) {
        return studentRepo.findAllByResult(result);
    }
}
