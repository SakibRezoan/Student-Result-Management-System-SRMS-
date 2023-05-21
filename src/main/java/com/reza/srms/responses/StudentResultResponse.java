package com.reza.srms.responses;

import com.reza.srms.entities.StudentResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StudentResultResponse implements Serializable {

    private Long studentRoll;

    private String studentName;

    private String studentNameBn;

    private Integer totalMarksInTheoryExam;

    private Integer totalMarksInLabExam;

    private Float obtainedMarksInTheoryExam;

    private Float obtainedMarksInLabExam;

    private Float obtainedMarksInScale100;

    private Float gpa;

    private String grade;

    public static StudentResultResponse makeResponse(StudentResult studentResult) {
        StudentResultResponse response = new StudentResultResponse();
        response.setStudentRoll(studentResult.getStudent().getRoll());
        response.setStudentName(studentResult.getStudent().getName());
        response.setStudentNameBn(studentResult.getStudent().getNameBn());
        response.setTotalMarksInTheoryExam(studentResult.getTotalMarksInTheoryExam());
        response.setTotalMarksInLabExam(studentResult.getTotalMarksInLabExam());
        response.setObtainedMarksInTheoryExam(studentResult.getObtainedMarksInTheoryExam());
        response.setObtainedMarksInLabExam(studentResult.getObtainedMarksInLabExam());
        response.setObtainedMarksInScale100(studentResult.getObtainedMarksInScale100());
        response.setGpa(studentResult.getGpa());
        response.setGrade(studentResult.getGrade());

        return response;
    }
}
