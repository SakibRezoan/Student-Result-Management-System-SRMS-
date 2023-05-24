package com.reza.srms.responses;

import com.reza.srms.entities.SemesterWiseResult;
import com.reza.srms.enums.Semester;
import com.reza.srms.enums.SemesterStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class SemesterWiseResultResponse implements Serializable {
    private Long id;

    private Float cgpa;

    private String grade;

    private Semester semester;

    private SemesterStatus semesterStatus;

    private List<CourseWiseResultResponse> courseWiseResultList;

    public static SemesterWiseResultResponse makeResponse(SemesterWiseResult semesterWiseResult) {
        SemesterWiseResultResponse response = new SemesterWiseResultResponse();
        response.setId(semesterWiseResult.getId());
        response.setCgpa(semesterWiseResult.getCgpa());
        response.setGrade(semesterWiseResult.getGrade());
        response.setSemester(semesterWiseResult.getSemester());
        response.setSemesterStatus(semesterWiseResult.getSemesterStatus());
        if (nonNull(semesterWiseResult.getCourseWiseResultList()))
            response.setCourseWiseResultList(semesterWiseResult.getCourseWiseResultList()
                    .stream().map(CourseWiseResultResponse::makeResponse).toList());

        return response;
    }
}
