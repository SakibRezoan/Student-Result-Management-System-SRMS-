package com.reza.srms.controllers;

import com.reza.srms.dtos.CourseDto;
import com.reza.srms.entities.Course;
import com.reza.srms.responses.CourseResponse;
import com.reza.srms.services.CourseService;
import com.reza.srms.utils.CommonDataHelper;
import com.reza.srms.validators.CourseValidator;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/course")
@Tag(name = "Course Controller", description = "Course API")
public class CourseController {

    private final CourseService courseService;

    private final CourseValidator courseValidator;

    private final CommonDataHelper commonDataHelper;

//    @GetMapping("/")
//    @Operation(summary = "Will show a welcome message", description = "Will show a welcome message", tags = {"welcome"})
//    @ApiResponse(responseCode = "200", description = "successful operation")
//    private String welcome() {
//        return "Welcome to Course Management System";
//    }

    @PostMapping("/save")
    @Operation(summary = "Add new course", description = "Add a new course", tags = {"addCourse"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    public ResponseEntity<JSONObject> save(@Valid @RequestBody CourseDto courseDto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(courseValidator, courseDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Course course = courseService.save(courseDto);

        return ok(success(CourseResponse.makeResponse(course), COURSE_SAVE).getJson());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find course by id", description = "Returns a single course", tags = {"findCourse"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<Course> course = courseService.findById(id);
        if (course.isEmpty())
            return badRequest().body(error(404, "Course not found with id: " + id).getJson());

        CourseResponse response = CourseResponse.makeResponse(course.get());

        return ok(success(response).getJson());
    }

    @PutMapping("/update")
    @Operation(summary = "Update a course", description = "Update a course", tags = {"updateCourse"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class)))

    public ResponseEntity<JSONObject> update(@Valid @RequestBody CourseDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());

        }
        Optional<Course> course = courseService.findById(dto.getId());
        if (course.isEmpty())
            return badRequest().body(error(null, "Course not found with id: " + dto.getId()).getJson());

        Course updatedCourse = courseService.update(course.get(), dto);

        return ok(success(CourseResponse.makeResponse(updatedCourse), COURSE_UPDATE).getJson());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a course", description = "Delete a course by id", tags = {"deleteCourse"})
    @ApiResponse(responseCode = "200", description = "successful operation")
    public ResponseEntity<JSONObject> delete(@PathVariable Long id) {

        Optional<Course> course = courseService.findById(id);
        if (course.isEmpty())
            return badRequest().body(error(null, "Course not found with id: " + id).getJson());

        courseService.delete(course.get());

        return ok(success(null, COURSE_DELETE).getJson());
    }
//    @GetMapping("/list")
//    public ResponseEntity<JSONObject> getList(
//            @RequestParam(value = "page", defaultValue = "1") Integer page,
//            @RequestParam(value = "size", defaultValue = "10") Integer size,
//            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
//            @RequestParam(value = "courseCode", defaultValue = "") String courseCode,
//            @RequestParam(value = "courseTitle", defaultValue = "") String courseTitle
//    ) {
//        helper.setPageSize(page, size);
//
//        PaginatedResponse paginatedResponse = new PaginatedResponse();
//
//        Map<String, Object> courseMappedSearchResult = courseService.getList(courseCode, courseTitle,
//                page, size, sortBy);
//        List<Course> responses = (List<Course>) courseMappedSearchResult.get("lists");
//
//        List<CourseResponse> customResponse = responses.stream().map(CourseResponse::makeResponse).
//                collect(Collectors.toList());
//        helper.getCommonData(page, size, courseMappedSearchResult, paginatedResponse, customResponse);
//
//        return ok(paginatedSuccess(paginatedResponse).getJson());
//    }

}
