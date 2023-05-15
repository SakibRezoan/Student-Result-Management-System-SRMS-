package com.reza.srms.controllers;

import com.reza.srms.entities.Course;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.services.CourseService;
import com.reza.srms.services.CourseWiseResultService;
import com.reza.srms.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.reza.srms.constant.ResponseStatus.SUCCESS;
import static com.reza.srms.utils.ResponseBuilder.error;
import static com.reza.srms.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/result")
@Tag(name = "Result Controller", description = "Result API")
public class CourseWiseResultController {

    private final CourseWiseResultService courseWiseResultService;

    private final CourseService courseService;

    private final StudentService studentService;

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @Operation(summary = "upload result of students", description = "upload result of students of specific Batch, Semester and Course")
    public ResponseEntity<?> upload(@RequestParam(value = "batchNo") Integer batchNo,
                                    @RequestParam(value = "semester") SemesterStatus semesterStatus,
                                    @RequestParam(value = "courseId") Long courseId,
                                    @RequestPart(value = "resultFile") MultipartFile resultFile) {

        Optional<Course> course = courseService.findById(courseId);
        if (course.isEmpty()) {
            return badRequest().body(error(null, "Course not found with courseId: " + courseId).getJson());
        }
        if (semesterStatus.equals(SemesterStatus.PASSED) || semesterStatus.equals(SemesterStatus.DROPPED))
            return badRequest().body(error(null, "Invalid semester...").getJson());

        String message = courseWiseResultService.uploadResult(batchNo, semesterStatus, course.get(), resultFile);

        if (message.equals(SUCCESS))
            return ok(success(null, "Result uploaded successfully").getJson());
        else return badRequest().body(error(null, message).getJson());
    }
}
