package com.reza.student_result.repositories;

import com.reza.student_result.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    List <Student> findByStudentName(String name);
    Student findByStudentEmail(String email);
    Student findByStudentRoll(Long roll);
    List<Student> findAllByStudentResult(String result);
}
