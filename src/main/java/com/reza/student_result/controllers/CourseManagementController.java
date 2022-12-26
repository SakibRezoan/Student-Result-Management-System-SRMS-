package com.reza.student_result.controllers;

import com.reza.student_result.entities.Course;
import com.reza.student_result.requests.CourseRequest;
import com.reza.student_result.response.CourseResponse;
import com.reza.student_result.services.impl.CourseServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.validators.CourseValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.reza.student_result.constant.MessageConstants.COURSE_SAVE;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.error;
import static com.reza.student_result.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/iit_course_management")
public class CourseManagementController {

    private final CourseServiceImpl courseServiceImpl;

    private final CourseValidator validator;
    private final CommonDataHelper helper;
    @GetMapping("/")
    private String Welcome() {
        return "Welcome to IIT Course Management System";
    }

    //Teacher::

    //Add Course
    @PostMapping("/teacher/addNewCourse")
    @Operation(summary = "Add new Course", description = "Add new Course")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody CourseRequest courseRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, courseRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Course course = courseServiceImpl.save(courseRequest);
        return ok(success(CourseResponse.from(course), COURSE_SAVE).getJson());
    }


    //Update Course

    //Register Student

    //Save Student Result

    //View All Student

    //Find one Student

    //IIT_Student::

    //View Result by roll



}
