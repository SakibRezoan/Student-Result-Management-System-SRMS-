package com.reza.srms.controllers;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.RecordStatus;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.exceptions.ResourceNotFoundException;
import com.reza.srms.response.StudentResponse;
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
        return "Welcome to IIT Student Management System";
    }

    //Register Student
    @PostMapping("/add-new-student")
    public ResponseEntity<JSONObject> addNewStudent(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(studentValidator, studentDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentService.save(studentDto);
        return ok(success(StudentResponse.from(student), STUDENT_SAVE).getJson());
    }

    //Find Student by id
    @GetMapping("/find-student/{id}")
    public ResponseEntity<JSONObject> findStudentById(@PathVariable Long id) {
        Optional<StudentResponse> response = Optional.ofNullable(studentService.findStudentById(id)
                .map(StudentResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }

    //Update IIT Student
    @PutMapping("/update-student")

    public ResponseEntity<JSONObject> updateStudent(@RequestBody StudentDto request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentService.update(request);
        return ok(success(StudentResponse.from(student), STUDENT_UPDATE).getJson());
    }

    //Update Record Status of Student
    @PutMapping("/change-record-status/{id}/{status}")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        Student student = studentService.update(id, status);
        return ok(success(StudentResponse.from(student), RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Students
    @GetMapping("/list")
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "roll", defaultValue = "") Long roll,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "studentEmail", defaultValue = "") String studentEmail,
            @RequestParam(value = "passingYear", defaultValue = "") Integer passingYear,
            @RequestParam(value = "semesterStatus", defaultValue = "") SemesterStatus semesterStatus,
            @RequestParam(value = "cgpa", defaultValue = "") Double cgpa

    ) {
        commonDataHelper.setPageSize(page, size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map<String, Object> searchResult = studentService.search(
                roll, name, studentEmail, passingYear, semesterStatus, cgpa,
                page, size, sortBy);
        List<Student> responses = (List<Student>) searchResult.get("lists");

        List<StudentResponse> customResponse = responses.stream().map(StudentResponse::from).
                collect(Collectors.toList());
        commonDataHelper.getCommonData(page, size, searchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }
}
