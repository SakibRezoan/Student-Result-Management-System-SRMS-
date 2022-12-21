package com.reza.student_result.services;

import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.entities.Student;
import com.reza.student_result.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class StudentService{

    protected final StudentRepository studentRepository;

    protected abstract Student save (StudentRequest studentRequest);

    protected abstract Student update (StudentRequest studentRequest);

    protected abstract Optional<Student> findById(Long id);

    public Student findStudentById(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student was not found for parameters {id=%s}", id));
        }
        return student.get();
    }

//    public List<Student> fetchAllStudents () throws StudentNotFoundException {
//        return studentRepository.findAll();
//    }
//
//    @Override
//    public List<Student> findPaginated(int pageNo, int pageSize, String sortBy) {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Student> pagedResult = studentRepository.findAll(paging);
//        return pagedResult.toList();
//    }
//
//    public List<Student> getStudentByName (String name) {
//        return studentRepository.findByStudentName(name);
//    }
//
//
//    public Student updateStudent (Student student) {
//        return studentRepository.save(student);
//    }
//
//    public String deleteOne (int id) {
//        try {
//            studentRepository.deleteById(id);
//        } catch (Exception e) {
//            return "Failed to delete the user";
//        }
//        return "Deleted Successfully";
//    }
//    public Student getStudentByEmail(String email) {
//        return studentRepository.findByStudentEmail(email);
//    }
//
//    public Student getStudentByRoll(long roll) {
//        return studentRepository.findByStudentRoll(roll);
//    }
//    public List<Student> getStudentsByResult(String result) {
//        return studentRepository.findAllByStudentResult(result);
//    }
    protected abstract Student saveEnclosure(Student student);

    protected abstract List<Student> getStudentByName(String studentName);
}
