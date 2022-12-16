package com.reza.student_result.repositories;

import com.reza.student_result.entities.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryForPagingAndSorting extends PagingAndSortingRepository <Student, Integer> {

}
