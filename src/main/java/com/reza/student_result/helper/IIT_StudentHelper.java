package com.reza.student_result.helper;

import com.reza.student_result.entities.Course;
import com.reza.student_result.entities.IIT_Result;
import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.services.impl.CourseServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.reza.student_result.utils.StringUtils.isNotEmpty;
@Component
@RequiredArgsConstructor
public class IIT_StudentHelper {
    private final CourseServiceImpl courseServiceImpl;

    public void validateCourse(Long courseId, Integer marksObtained) {
        if (isNotEmpty(courseId)) {
            Optional<Course> course = courseServiceImpl.findCourseById(courseId);
            if (course.isEmpty()){
                throw new ResourceNotFoundException("Course was not found with the id = {%s}" + course);
            }
        }
        if (isNotEmpty(marksObtained)){
            if (marksObtained > 100 || marksObtained < 0){
                throw new RuntimeException("Marks should be 0 to 100");
            }
        }

    }
    public IIT_Student saveCourseResult(IIT_Student iitStudent, Long courseId, Integer marksObtained) {
        List<IIT_Result> iitResults = new ArrayList<>();
        if (iitStudent.getIitResults() != null) {
            if (! iitStudent.getIitResults().isEmpty()) {
                List<IIT_Result> lists = new ArrayList<>(iitStudent.getIitResults());
                iitResults.addAll(lists);
            }
        }
        IIT_Result iitResult = new IIT_Result();
        iitResult.setCourse(courseServiceImpl.findCourseByIdNotOptional(courseId));
        iitResult.setMarksObtained(marksObtained);
        if (marksObtained >= 80 && marksObtained <= 100) {
            iitResult.setGradePoint(4.0);
            iitResult.setLetterGrade("A+");
        }
        else if (marksObtained >= 75 && marksObtained <= 79) {
            iitResult.setGradePoint(3.75);
            iitResult.setLetterGrade("A");
        }
        else if (marksObtained >= 70 && marksObtained <= 74) {
            iitResult.setGradePoint(3.5);
            iitResult.setLetterGrade("A-");
        }
        else if (marksObtained >= 65 && marksObtained <= 69) {
            iitResult.setGradePoint(3.25);
            iitResult.setLetterGrade("B+");
        }
        else if (marksObtained >= 60 && marksObtained <= 64) {
            iitResult.setGradePoint(3.0);
            iitResult.setLetterGrade("B");
        }
        else if (marksObtained >= 55 && marksObtained <= 59) {
            iitResult.setGradePoint(2.75);
            iitResult.setLetterGrade("B-");
        }
        else if (marksObtained >= 50 && marksObtained <= 54) {
            iitResult.setGradePoint(2.5);
            iitResult.setLetterGrade("C+");
        }
        else if (marksObtained >= 45 && marksObtained <= 49) {
            iitResult.setGradePoint(2.25);
            iitResult.setLetterGrade("C");
        }
        else if (marksObtained >= 40 && marksObtained <= 44) {
            iitResult.setGradePoint(2.0);
            iitResult.setLetterGrade("D");
        }
        else if (marksObtained >= 0 && marksObtained <= 39) {
            iitResult.setGradePoint(0.0);
            iitResult.setLetterGrade("F");
        }

        iitResults.add(iitResult);
        iitStudent.addResults(iitResults);
        return iitStudent;

    }
}
