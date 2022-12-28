package com.reza.student_result.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.StudentRequest;
import com.reza.student_result.entities.Enclosure;
import com.reza.student_result.helper.StudentHelper;
import com.reza.student_result.response.StudentResponse;
import com.reza.student_result.services.impl.StudentServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.utils.PaginatedResponse;
import com.reza.student_result.validators.StudentValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reza.student_result.constant.MessageConstants.*;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.*;
import static com.reza.student_result.utils.StringUtils.nonNull;
import static com.reza.student_result.utils.StringUtils.isEmpty;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/student")
public class StudentResultController {
    private final StudentServiceImpl studentServiceImpl;
    private final StudentValidator validator;
    private final StudentHelper studentHelper;

    private final CommonDataHelper helper;

    @GetMapping("/")
    private String Welcome() {
        return "Welcome";
    }

    @GetMapping("list")
    @Operation(summary = "Fetch all students", description =
            "Fetch all students with page, size, sortBy, Roll, Name, Email & Result ")
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "studentRoll", defaultValue = "") Long studentRoll,
            @RequestParam(value = "studentName", defaultValue = "") String studentName,
            @RequestParam(value = "studentEmail", defaultValue = "") String studentEmail,
            @RequestParam(value = "studentResult", defaultValue = "") String studentResult
    ) {
        helper.setPageSize(page,size);
        PaginatedResponse paginatedResponse = new PaginatedResponse();
        Map<String,Object> studentMappedSearchResult = studentServiceImpl.searchStudent(
                                    studentRoll, studentName, studentEmail, studentResult,
                                    page, size, sortBy);
        List<Student> responses =(List<Student>) studentMappedSearchResult.get("lists");

        List<StudentResponse> customResponse = responses.stream().map(StudentResponse::from).
                                                collect(Collectors.toList());
        helper.getCommonData(page,size, studentMappedSearchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }

    @PostMapping("/save")
    @Operation(summary = "Save student", description = "Save new Student")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody StudentRequest studentRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, studentRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentServiceImpl.save(studentRequest);
        return ok(success(StudentResponse.from(student), STUDENT_SAVE).getJson());
    }

    @RequestMapping(
            path = "/save/enclosure/{studentId}",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    @Operation(summary = "Save enclosure", description = "Save enclosure of student with id")
    public ResponseEntity<JSONObject> saveEnclosure(@PathVariable("studentId") @NotNull Long studentId,
                                                    @RequestPart(value = "file", required = false) MultipartFile file,
                                                    @RequestParam(value = "request", required = false) String request)
                                                    throws ResourceNotFoundException {

        Student student = studentServiceImpl.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student " + studentId));

        if (nonNull(file) && isEmpty(request))
            return badRequest().body(
                    error("file data must be selected").getJson());

        List<Enclosure> enclosures = null;
        try {
            enclosures = studentHelper.getStudentEnclosures(file, request, student);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        student.addEnclosures(enclosures);
        studentServiceImpl.saveEnclosure(student);
        return ok(success(null, "success").getJson());
    }

    @PutMapping("/update")
    @Operation(summary = "Update Student", description = "Update existing student")

    public ResponseEntity<JSONObject> update(@Valid @RequestBody StudentRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Student student = studentServiceImpl.update(request);
        return ok(success(StudentResponse.from(student), STUDENT_UPDATE).getJson());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find one student", description = "Find a student by id")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<StudentResponse> response = Optional.ofNullable(studentServiceImpl.findStudentById(id)
                .map(StudentResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }
    @PutMapping("/change-record-status/{id}/{status}")
    @Operation(summary = "Change record status of student", description =
            "Change record status of student with parameter id and status")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {
        Student student = studentServiceImpl.update(id, status);
        return ok(success(StudentResponse.from(student), RECORD_STATUS_UPDATE).getJson());
    }


//    @PostMapping("/update")
//    public Student updateStudent (@RequestBody Student student) {
//        return studentService.updateStudent(student);
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteOne (@PathVariable int id) {
//        return studentService.deleteOne(id);
//    }
//
//    @GetMapping("/fetchAll")
//    private ResponseEntity<List<Student>> getAllStudents () throws StudentNotFoundException {
//        return  ResponseEntity.ok(studentService.fetchAllStudents());
//    }

//
//    @GetMapping("/getStudentByName/{name}")
//    public ResponseEntity<List<Student>> getStudentByName (@PathVariable String name) {
//        return ResponseEntity.ok(studentService.getStudentByName(name));
//    }
//
//    @GetMapping("/getStudentByEmail/{email}")
//    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(studentService.getStudentByEmail(email));
//    }
//
//    @GetMapping("/getStudentByRoll/{roll}")
//    public ResponseEntity<Student> getStudentByRoll (@PathVariable long roll) {
//        return ResponseEntity.ok(studentService.getStudentByRoll(roll));
//    }
//
//    @GetMapping("/getStudentsByResult/{result}")
//    public ResponseEntity<List<Student>> getStudentsByResult (@PathVariable String result) {
//        return ResponseEntity.ok(studentService.getStudentsByResult(result));
//    }
//
//    @GetMapping("/getPaginated/{pageNo}/{pageSize}/{sortBy}")
//    public List<Student> getPaginatedStudents(@PathVariable int pageNo,
//                                              @PathVariable int pageSize, @PathVariable String sortBy) {
//
//        return studentService.findPaginated(pageNo, pageSize, sortBy);
//    }


}
