package com.reza.srms.responses;

import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.enums.Semester;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseWiseResultResponse implements Serializable {

    private Long id;

    private String courseTitle;

    private Integer totalCredits;

    private Integer noOfCreditsInTheory;

    private Integer noOfCreditsInLab;

    private Integer batchNo;

    private Semester semester;

    private String resultFileName;


    public static CourseWiseResultResponse makeResponse(CourseWiseResult courseWiseResult) {
        CourseWiseResultResponse response = new CourseWiseResultResponse();
        response.setId(courseWiseResult.getId());
        response.setCourseTitle(courseWiseResult.getCourse().getCourseTitle());
        response.setTotalCredits(courseWiseResult.getCourse().getTotalCredits());
        response.setNoOfCreditsInTheory(courseWiseResult.getCourse().getNoOfCreditsInTheory());
        response.setNoOfCreditsInLab(courseWiseResult.getCourse().getNoOfCreditsInLab());
        response.setResultFileName(courseWiseResult.getFileName());

        return response;
    }
}
