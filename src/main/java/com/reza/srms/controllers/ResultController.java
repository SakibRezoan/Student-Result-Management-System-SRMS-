package com.reza.srms.controllers;

import com.reza.srms.entities.Course;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.services.CourseService;
import com.reza.srms.services.ResultService;
import com.reza.srms.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
public class ResultController {

    private final ResultService resultService;

    private final CourseService courseService;

    private final StudentService studentService;

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @Operation(summary = "upload result of students", description = "upload result of students of specific Batch, Semester and Course")
    public ResponseEntity<?> upload(@RequestParam(value = "batchNo") Integer batchNo,
                                    @RequestParam(value = "semester") SemesterStatus semester,
                                    @RequestParam(value = "courseId") Long courseId,
                                    @RequestPart(value = "resultFile") MultipartFile resultFile) {

        Optional<Course> course = courseService.findById(courseId);
        if (course.isEmpty()) {
            return badRequest().body(error(null, "Course not found with courseId: " + courseId).getJson());
        }
        if (semester.equals(SemesterStatus.PASSED) || semester.equals(SemesterStatus.DROPPED))
            return badRequest().body(error(null, "Invalid semester...").getJson());

        List<Student> studentList = studentService.findByBatchAndSemester(batchNo, semester);

        String message = resultService.uploadResult(studentList, course.get(), resultFile);

        if (message.equals(SUCCESS))
            return ok(success(null, "Result uploaded successfully").getJson());
        else return badRequest().body(error(null, message).getJson());
    }
}
