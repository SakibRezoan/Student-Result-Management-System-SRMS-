package com.reza.srms.controllers;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.responses.StudentResponse;
import com.reza.srms.services.StudentService;
import com.reza.srms.utils.CommonDataHelper;
import com.reza.srms.utils.PaginatedResponse;
import com.reza.srms.validators.StudentValidator;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reza.srms.constant.MessageConstants.*;
import static com.reza.srms.exceptions.ApiError.fieldError;
import static com.reza.srms.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/student")
public class StudentController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;
    //    private final StudentHelper studentHelper;
    private final CommonDataHelper commonDataHelper;

    @GetMapping("/")
    private String welcome() {
        return "Welcome to Student Management System";
    }

    //Register Student
    @PostMapping("/add")
    public ResponseEntity<JSONObject> add(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(studentValidator, studentDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentService.save(studentDto);
        return ok(success(StudentResponse.makeResponse(student), STUDENT_SAVE).getJson());
    }

    //Find Student by id
    @GetMapping("/find/{id}")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty())
            return badRequest().body(error(404, "Student not found with id: " + id).getJson());

        return ok(success(StudentResponse.makeResponse(student.get())).getJson());
    }

    //Update IIT Student
    @PutMapping("/update")

    public ResponseEntity<JSONObject> updateStudent(@RequestBody StudentDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Optional<Student> student = studentService.findById(dto.getId());
        if (student.isEmpty())
            return badRequest().body(error(404, "Student not found with id: " + dto.getId()).getJson());


        Student updatedStudent = studentService.update(dto, student.get());

        return ok(success(StudentResponse.makeResponse(updatedStudent), STUDENT_UPDATE).getJson());
    }

    //Update Record Status of Student
    @PutMapping("/change-record-status/{id}/{status}")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty())
            return badRequest().body(error(404, "Student not found with id: " + id).getJson());

        Student updatedStudent = studentService.updateRecordStatus(student.get(), status);

        return ok(success(null, RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Students
    @GetMapping("/list")
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "roll", defaultValue = "") Long roll,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "passingYear", defaultValue = "") Integer passingYear,
            @RequestParam(value = "semesterStatus", defaultValue = "") SemesterStatus semesterStatus,
            @RequestParam(value = "cgpa", defaultValue = "") Float cgpa

    ) {
        commonDataHelper.setPageSize(page, size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map<String, Object> searchResult = studentService.search(
                roll, name, email, passingYear, semesterStatus, cgpa,
                page, size, sortBy);
        List<Student> responses = (List<Student>) searchResult.get("lists");

        List<StudentResponse> customResponse = responses.stream().map(StudentResponse::makeResponse).
                collect(Collectors.toList());
        commonDataHelper.getCommonData(page, size, searchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }
}
