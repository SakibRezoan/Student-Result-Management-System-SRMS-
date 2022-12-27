package com.reza.student_result.controllers;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.response.IIT_StudentResponse;
import com.reza.student_result.services.impl.IIT_StudentServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.validators.IIT_StudentValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.reza.student_result.constant.MessageConstants.IIT_STUDENT_SAVE;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.error;
import static com.reza.student_result.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/iit-student-management")
public class IIT_StudentManagementController {
    private final IIT_StudentServiceImpl iitStudentServiceImpl;
    private final IIT_StudentValidator validator;
    private final CommonDataHelper helper;

    @GetMapping("/")
    @Operation(summary = "Welcome Message", description = "Welcome Message")
    private String welcome() {
        return "Welcome to IIT Student Management System";
    }

    //Register IIT_Student
    @PostMapping("/teacher/add-new-iit-student")
    @Operation(summary = "Add new IIT_Student", description = "Add new IIT_Student")
    public ResponseEntity<JSONObject> addNewIitStudent(@Valid @RequestBody IIT_StudentRequest iit_studentRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, iit_studentRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        IIT_Student iitStudent = iitStudentServiceImpl.save(iit_studentRequest);
        return ok(success(IIT_StudentResponse.from(iitStudent), IIT_STUDENT_SAVE).getJson());
    }

}
