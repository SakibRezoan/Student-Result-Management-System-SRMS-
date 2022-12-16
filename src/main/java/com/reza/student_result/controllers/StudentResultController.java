package com.reza.student_result.controllers;

import com.reza.student_result.dto.StudentRequest;
import com.reza.student_result.entities.Student;
import com.reza.student_result.exceptions.StudentNotFoundException;
import com.reza.student_result.repositories.StudentRepo;
import com.reza.student_result.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentResultController {

    @Autowired
    StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("/")
    private String Welcome() {
        return "Welcome";
    }

    @PostMapping("/save")
    private ResponseEntity<Student> saveNewStudent (@RequestBody @Valid StudentRequest studentRequest) {
        return new ResponseEntity<>(studentService.saveNewStudent(studentRequest), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public Student updateStudent (@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @GetMapping("/delete/{id}")
    public String deleteOne (@PathVariable int id) {
        return studentService.deleteOne(id);
    }

    @GetMapping("/fetchAll")
    private ResponseEntity<List<Student>> getAllStudents () throws StudentNotFoundException {
        return  ResponseEntity.ok(studentService.fetchAllStudents());
    }
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Student> getOneStudent (@PathVariable int id) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/getStudentByName/{name}")
    public ResponseEntity<List<Student>> getStudentByName (@PathVariable String name) {
        return ResponseEntity.ok(studentService.getStudentByName(name));
    }

    @GetMapping("/getStudentByEmail/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.getStudentByEmail(email));
    }

    @GetMapping("/getStudentByRoll/{roll}")
    public ResponseEntity<Student> getStudentByRoll (@PathVariable long roll) {
        return ResponseEntity.ok(studentService.getStudentByRoll(roll));
    }

    @GetMapping("/getStudentsByResult/{result}")
    public ResponseEntity<List<Student>> getStudentsByResult (@PathVariable String result) {
        return ResponseEntity.ok(studentService.getStudentsByResult(result));
    }

    @GetMapping("/getPaginated/{pageNo}/{pageSize}/{sortBy}")
    public List<Student> getPaginatedStudents(@PathVariable int pageNo,
                                              @PathVariable int pageSize, @PathVariable String sortBy) {

        return studentService.findPaginated(pageNo, pageSize, sortBy);
    }


}
