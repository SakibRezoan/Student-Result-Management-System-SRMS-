package com.reza.srms.dtos;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseWiseResultImportDto implements Serializable {

    @ExcelCellName("Roll")
    private Long roll;

    @ExcelCellName("TheoryOutOf")
    private Integer totalMarksInTheoryExam;

    @ExcelCellName("LabOutOf")
    private Integer totalMarksInLabExam;

    @ExcelCellName("TheoryObtained")
    private Float obtainedMarksInTheoryExam;

    @ExcelCellName("LabObtained")
    private Float obtainedMarksInLabExam;
}
