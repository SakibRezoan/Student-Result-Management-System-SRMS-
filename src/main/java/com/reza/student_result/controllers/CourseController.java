package com.reza.student_result.controllers;

import com.reza.student_result.dtos.CourseDto;
import com.reza.student_result.entities.Course;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.response.CourseResponse;
import com.reza.student_result.services.CourseService;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.utils.PaginatedResponse;
import com.reza.student_result.validators.CourseValidator;
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
@RequestMapping("api/course")
public class CourseController {

    private final CourseService courseService;
    private final CourseValidator validator;
    private final CommonDataHelper helper;

    @GetMapping("/")
    private String welcome() {
        return "Welcome to IIT Course Management System";
    }

    /*    ****Teacher****   */

    //Add Course
    @PostMapping("/add-new-course")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody CourseDto courseDto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, courseDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Course course = courseService.save(courseDto);
        return ok(success(CourseResponse.from(course), COURSE_SAVE).getJson());
    }

    //Find Course by id
    @GetMapping("/find-course/{id}")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<CourseResponse> response = Optional.ofNullable(courseService.findCourseById(id)
                .map(CourseResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }

    //Update Course
    @PutMapping("/update-course")

    public ResponseEntity<JSONObject> update(@Valid @RequestBody CourseDto request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Course course = courseService.update(request);
        return ok(success(CourseResponse.from(course), COURSE_UPDATE).getJson());
    }

    //Change Record Status of Course
    @PutMapping("/change-record-status/{id}/{status}")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        Course course = courseService.update(id, status);
        return ok(success(CourseResponse.from(course), RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Courses
    @GetMapping("/teacher/course-list")
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "courseCode", defaultValue = "") String courseCode,
            @RequestParam(value = "courseTitle", defaultValue = "") String courseTitle
    ) {
        helper.setPageSize(page, size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map<String, Object> courseMappedSearchResult = courseService.searchCourse(courseCode, courseTitle,
                page, size, sortBy);
        List<Course> responses = (List<Course>) courseMappedSearchResult.get("lists");

        List<CourseResponse> customResponse = responses.stream().map(CourseResponse::from).
                collect(Collectors.toList());
        helper.getCommonData(page, size, courseMappedSearchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }

}
