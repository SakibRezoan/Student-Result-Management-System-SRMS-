package com.reza.student_result.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reza.student_result.dto.StudentRequest;
import com.reza.student_result.entities.Enclosure;
import com.reza.student_result.entities.Student;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.exceptions.StudentNotFoundException;
import com.reza.student_result.helper.StudentHelper;
import com.reza.student_result.repositories.StudentRepo;
import com.reza.student_result.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.reza.student_result.utils.ResponseBuilder.error;
import static com.reza.student_result.utils.ResponseBuilder.success;
import static com.reza.student_result.utils.StringUtils.isEmpty;
import static com.reza.student_result.utils.StringUtils.nonNull;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentResultController {



    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepo studentRepo;
    private final StudentHelper studentHelper;

    @GetMapping("/")
    private String Welcome() {
        return "Welcome";
    }

    @PostMapping("/save")
    private ResponseEntity<Student> saveNewStudent (@RequestBody @Valid StudentRequest studentRequest) {
        return new ResponseEntity<>(studentService.saveNewStudent(studentRequest), HttpStatus.CREATED);
    }

    @RequestMapping(
            path = "/save/enclosure/{studentId}",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<JSONObject> saveEnclosure(@PathVariable("studentId") @NotNull int studentId,
                                                    @RequestPart(value = "file", required = false) MultipartFile file,
                                                    @RequestParam(value = "request", required = false) String request) {

        Student student = studentService.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student " + studentId));

        if (nonNull(file) && request.isEmpty())
            return badRequest().body(
                    error("file data must be selected").getJson());

        List<Enclosure> enclosures = null;
        try {
            enclosures = studentHelper.getStudentEnclosures(file, request, student);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        student.addEnclosures(enclosures);

        studentService.saveEnclosure(student);
        return ok(success(null, "success").getJson());
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
    public ResponseEntity<Optional<Student>> getOneStudent (@PathVariable int id) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.findById(id));
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
