package com.reza.student_result.dto;

import com.reza.student_result.entities.Student;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class StudentDto {


    private Long id;

    private Long roll;

    private String name;

    private String email;

    private String result;

    public static StudentDto response(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        return dto;
    }

    public Student to() {
        Student student = new Student();
        student.setName(this.name);
//        student.setCourseList(this.courseList);
//      student.setEnclosure(this.enclosure);
        return student;
    }

}
