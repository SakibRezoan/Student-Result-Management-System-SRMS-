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

    @ExcelCellName("marks")
    private Float obtainedMarks;
}
