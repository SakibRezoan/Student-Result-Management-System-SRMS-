package com.reza.student_result.controllers;

import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.CourseRequest;
import com.reza.student_result.response.CourseResponse;
import com.reza.student_result.services.impl.CourseServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.utils.PaginatedResponse;
import com.reza.student_result.validators.CourseValidator;

import io.swagger.v3.oas.annotations.Operation;

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

import static com.reza.student_result.constant.MessageConstants.*;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/iit-course-management")
public class IIT_CourseController {

    private final CourseServiceImpl courseServiceImpl;
    private final CourseValidator validator;
    private final CommonDataHelper helper;
    @GetMapping("/")
    @Operation(summary = "Welcome Message", description = "Welcome Message")
    private String welcome() {
        return "Welcome to IIT Course Management System";
    }

    /*    ****Teacher****   */

    //Add Course
    @PostMapping("/teacher/add-new-course")
    @Operation(summary = "Add new Course", description = "Add new Course")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody CourseRequest courseRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, courseRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Course course = courseServiceImpl.save(courseRequest);
        return ok(success(CourseResponse.from(course), COURSE_SAVE).getJson());
    }
    //Find Course by id
    @GetMapping("/teacher/find-course/{id}")
    @Operation(summary = "Find a course", description = "Find a course by id")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<CourseResponse> response = Optional.ofNullable(courseServiceImpl.findCourseById(id)
                .map(CourseResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }
    //Update Course
    @PutMapping("/teacher/update-course")
    @Operation(summary = "Update Course", description = "Update existing course")

    public ResponseEntity<JSONObject> update(@Valid @RequestBody CourseRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Course course = courseServiceImpl.update(request);
        return ok(success(CourseResponse.from(course), COURSE_UPDATE).getJson());
    }

    //Update Record Status of Course
    @PutMapping("/change-record-status/{id}/{status}")
    @Operation(summary = "Change record status of a course", description =
            "Change record status of course with parameter id and status")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        Course course = courseServiceImpl.update(id, status);
        return ok(success(CourseResponse.from(course), RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Courses
    @GetMapping("/teacher/course-list")
    @Operation(summary = "Fetch all courses", description =
            "Fetch all courses with page, size, sortBy, courseCode, courseTitle")
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "courseCode", defaultValue = "") String courseCode,
            @RequestParam(value = "courseTitle", defaultValue = "") String courseTitle
    ) {
        helper.setPageSize(page,size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map<String,Object> courseMappedSearchResult = courseServiceImpl.searchCourse(courseCode, courseTitle,
                page, size, sortBy);
        List<Course> responses =(List<Course>) courseMappedSearchResult.get("lists");

        List<CourseResponse> customResponse = responses.stream().map(CourseResponse::from).
                                                collect(Collectors.toList());
        helper.getCommonData(page,size, courseMappedSearchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }

    /*     ****IIT_Student****     */

    //Get Result By Roll

}
