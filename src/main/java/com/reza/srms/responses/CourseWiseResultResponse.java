package com.reza.srms.responses;

import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.enums.SemesterStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseWiseResultResponse implements Serializable {

    private Long id;

    private String courseTitle;

    private Integer totalCredits;

    private Integer noOfCreditsInTheory;

    private Integer noOfCreditsInLab;

    private Integer batchNo;

    private SemesterStatus semesterStatus;

    private String resultFileName;

    private List<StudentResultResponse> studentResultList;

    public static CourseWiseResultResponse makeResponse(CourseWiseResult courseWiseResult) {
        CourseWiseResultResponse response = new CourseWiseResultResponse();
        response.setId(courseWiseResult.getId());
        response.setCourseTitle(courseWiseResult.getCourse().getCourseTitle());
        response.setTotalCredits(courseWiseResult.getCourse().getTotalCredits());
        response.setNoOfCreditsInTheory(courseWiseResult.getCourse().getNoOfCreditsInTheory());
        response.setNoOfCreditsInLab(courseWiseResult.getCourse().getNoOfCreditsInLab());
        response.setBatchNo(courseWiseResult.getBatchNo());
        response.setSemesterStatus(courseWiseResult.getSemesterStatus());
        response.setResultFileName(courseWiseResult.getFileName());
        response.setStudentResultList(courseWiseResult.getStudentResultList().stream().map(StudentResultResponse::makeResponse).toList());

        return response;
    }
}
