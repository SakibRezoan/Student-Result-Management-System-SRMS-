package com.reza.student_result.repositories;

import com.reza.student_result.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository <Student, Integer> {
    List <Student> findByName(String name);

    Student findByEmail(String email);
    Student findByRoll(long roll);

    List<Student> findAllByResult(String result);
}
