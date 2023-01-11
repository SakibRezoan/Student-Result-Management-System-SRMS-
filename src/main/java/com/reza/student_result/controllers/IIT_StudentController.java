package com.reza.student_result.controllers;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.enums.SemesterStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.helper.IIT_StudentHelper;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.response.IIT_StudentResponse;
import com.reza.student_result.services.impl.IIT_StudentServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.utils.PaginatedResponse;
import com.reza.student_result.validators.IIT_StudentValidator;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reza.student_result.constant.MessageConstants.*;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/iit-student-management")
public class IIT_StudentController {
    private final IIT_StudentServiceImpl iitStudentServiceImpl;
    private final IIT_StudentValidator iitStudentValidator;
    private final IIT_StudentHelper iitStudentHelper;
    private final CommonDataHelper helper;

    @GetMapping("/")
    @Operation(summary = "Welcome Message", description = "Welcome Message")
    private String welcome() {
        return "Welcome to IIT Student Management System";
    }

    //Register IIT_Student
    @PostMapping("/teacher/add-new-iit-student")
    @RolesAllowed("Teacher")
    @Operation(summary = "Add new IIT_Student", description = "Add new IIT_Student")
    public ResponseEntity<JSONObject> addNewIitStudent(@Valid @RequestBody IIT_StudentRequest iit_studentRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(iitStudentValidator, iit_studentRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        IIT_Student iitStudent = iitStudentServiceImpl.save(iit_studentRequest);
        return ok(success(IIT_StudentResponse.from(iitStudent), IIT_STUDENT_SAVE).getJson());
    }

    //Find IIT Student by id
    @GetMapping("/teacher/find-student/{id}")
//    @RolesAllowed("Teacher")
    @Operation(summary = "Find a student", description = "Find a student by id")
    public ResponseEntity<JSONObject> findStudentById(@PathVariable Long id) {
        Optional<IIT_StudentResponse> response = Optional.ofNullable(iitStudentServiceImpl.findStudentById(id)
                .map(IIT_StudentResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }
    //Update IIT Student
    @PutMapping("/teacher/update-iit-student")
//    @RolesAllowed("Teacher")
    @Operation(summary = "Update IIT Student", description = "Update existing iit student")

    public ResponseEntity<JSONObject> updateStudent(@RequestBody IIT_StudentRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        IIT_Student iitStudent = iitStudentServiceImpl.update(request);
        return ok(success(IIT_StudentResponse.from(iitStudent), IIT_STUDENT_UPDATE).getJson());
    }

    //Update Record Status of IIT Student
    @PutMapping("/change-record-status/{id}/{status}")
//    @RolesAllowed("Teacher")
    @Operation(summary = "Change record status of a iit student", description =
            "Change record status of a iit student with parameter id and status")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        IIT_Student iitStudent = iitStudentServiceImpl.update(id, status);
        return ok(success(IIT_StudentResponse.from(iitStudent), RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Students
    @GetMapping("/teacher/student-list")
//    @RolesAllowed("Teacher")
    @Operation(summary = "Fetch all students", description =
            "Fetch all students with page, size, sortBy, roll, name, email, passingYear, semesterStatus, cgpa")
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
        helper.setPageSize(page,size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map<String,Object> iitStudentMappedSearchResult = iitStudentServiceImpl.search(
                roll, name, studentEmail, passingYear, semesterStatus, cgpa,
                page, size, sortBy);
        List<IIT_Student> responses =(List<IIT_Student>) iitStudentMappedSearchResult.get("lists");

        List<IIT_StudentResponse> customResponse = responses.stream().map(IIT_StudentResponse::from).
                collect(Collectors.toList());
        helper.getCommonData(page,size, iitStudentMappedSearchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }

    //Teacher Upload Results
    @PutMapping("/teacher/upload-iit-student-result/{studentId}")
//    @RolesAllowed("Teacher")
    @Operation(summary = "Upload IIT Student Result", description = "Upload course wise result of a IIT Student")

    public ResponseEntity<JSONObject> uploadResult(@PathVariable @NotNull Long studentId ,
                                                   @RequestParam(value = "courseId") Long courseId,
                                                   @RequestParam(value = "marksObtained") Integer marksObtained) {

        IIT_Student iitStudent = iitStudentServiceImpl.findStudentById(studentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("IIT Student was not found with the id = {%s}" + studentId)
                );
        iitStudentHelper.validateCourse(courseId, marksObtained);

        iitStudent = iitStudentHelper.saveCourseResult(iitStudent, courseId, marksObtained );

        iitStudent = iitStudentServiceImpl.saveResults(iitStudent);

       return ok(success(iitStudent, IIT_STUDENT_RESULT_UPLOAD).getJson());
    }


}
