package com.reza.srms.dtos;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseWiseResultImportDto implements Serializable {

    @ExcelCellName("roll")
    private Long roll;

    @ExcelCellName("TheoryOutOf")
    private Integer totalMarksInTheoryExam;

    @ExcelCellName("TheoryObtained")
    private Integer totalMarksInLabExam;

    @ExcelCellName("LabOutOf")
    private Float obtainedMarksInTheoryExam;

    @ExcelCellName("LabObtained")
    private Float obtainedMarksInLabExam;
}
