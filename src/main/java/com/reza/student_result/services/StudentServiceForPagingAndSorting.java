package com.reza.student_result.services;

import com.reza.student_result.entities.Student;

import java.util.List;

public interface StudentServiceForPagingAndSorting {
    List<Student> findPaginated(int pageNo, int pageSize, String sortBy);
}
