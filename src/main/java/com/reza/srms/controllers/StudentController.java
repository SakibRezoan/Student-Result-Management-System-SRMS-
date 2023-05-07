package com.reza.srms.controllers;

import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.responses.StudentResponse;
import com.reza.srms.services.StudentService;
import com.reza.srms.utils.CommonDataHelper;
import com.reza.srms.validators.StudentValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.reza.srms.constant.MessageConstants.*;
import static com.reza.srms.exceptions.ApiError.fieldError;
import static com.reza.srms.utils.ResponseBuilder.error;
import static com.reza.srms.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/student")
@Tag(name = "Student Controller", description = "Student API")
public class StudentController {

    private final StudentService studentService;

    private final StudentValidator studentValidator;

    private final CommonDataHelper commonDataHelper;

    @GetMapping("/")
    @Operation(summary = "Will show a welcome message", description = "Will show a welcome message", tags = {"welcome"})
    @ApiResponse(responseCode = "200", description = "successful operation")
    private String welcome() {
        return "Welcome to Student Management System";
    }

    @PostMapping("/save")
    @Operation(summary = "Register a new student", description = "Register a new student", tags = {"addStudent"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = StudentResponse.class)))

    public ResponseEntity<JSONObject> save(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(studentValidator, studentDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentService.save(studentDto);

        return ok(success(StudentResponse.makeResponse(student), STUDENT_SAVE).getJson());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find student by id", description = "Returns a single student", tags = {"findStudent"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty())
            return badRequest().body(error(404, "Student not found with id: " + id).getJson());

        return ok(success(StudentResponse.makeResponse(student.get())).getJson());
    }

    @PutMapping("/update")
    @Operation(summary = "Update a student", description = "Update a student", tags = {"updateStudent"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = StudentResponse.class)))
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

    @PutMapping("/change-semester-status/{id}/{status}")
    @Operation(summary = "Change semester status of a course by id", description = "Change semester status of a course by id", tags = {"changeSemester"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    public ResponseEntity<JSONObject> changeSemesterStatus(@PathVariable Long id, @PathVariable SemesterStatus status) {

        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty())
            return badRequest().body(error(null, "Student not found with id: " + id).getJson());

        Student updatedStudent = studentService.changeSemesterStatus(student.get(), status);

        return ok(success(StudentResponse.makeResponse(updatedStudent), SEMESTER_STATUS_UPDATE).getJson());
    }

//    @GetMapping("/list")
//    public ResponseEntity<JSONObject> getList(
//            @RequestParam(value = "page", defaultValue = "1") Integer page,
//            @RequestParam(value = "size", defaultValue = "10") Integer size,
//            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
//            @RequestParam(value = "roll", defaultValue = "") Long roll,
//            @RequestParam(value = "name", defaultValue = "") String name,
//            @RequestParam(value = "email", defaultValue = "") String email,
//            @RequestParam(value = "passingYear", defaultValue = "") Integer passingYear,
//            @RequestParam(value = "semesterStatus", defaultValue = "") SemesterStatus semesterStatus,
//            @RequestParam(value = "cgpa", defaultValue = "") Float cgpa
//
//    ) {
//        commonDataHelper.setPageSize(page, size);
//
//        PaginatedResponse paginatedResponse = new PaginatedResponse();
//
//        Map<String, Object> searchResult = studentService.search(
//                roll, name, email, passingYear, semesterStatus, cgpa,
//                page, size, sortBy);
//        List<Student> responses = (List<Student>) searchResult.get("lists");
//
//        List<StudentResponse> customResponse = responses.stream().map(StudentResponse::makeResponse).
//                collect(Collectors.toList());
//        commonDataHelper.getCommonData(page, size, searchResult, paginatedResponse, customResponse);
//
//        return ok(paginatedSuccess(paginatedResponse).getJson());
//    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a student", description = "Delete a student by id", tags = {"deleteStudent"})
    @ApiResponse(responseCode = "200", description = "successful operation")

    public ResponseEntity<JSONObject> delete(@PathVariable Long id) {

        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty())
            return badRequest().body(error(null, "Student not found with id: " + id).getJson());

        studentService.delete(student.get());

        return ok(success(null, STUDENT_DELETE).getJson());
    }
}
