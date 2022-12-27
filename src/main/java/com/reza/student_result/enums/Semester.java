package com.reza.student_result.enums;

public enum Semester {
    FIRST_SEMESTER("FIRST_SEMESTER"),
    SECOND_SEMESTER("SECOND_SEMESTER"),
    THIRD_SEMESTER("THIRD_SEMESTER"),
    FOURTH_SEMESTER("FOURTH_SEMESTER"),
    FIFTH_SEMESTER("FIFTH_SEMESTER"),
    SIXTH_SEMESTER("SIXTH_SEMESTER"),
    SEVENTH_SEMESTER("SEVENTH_SEMESTER"),
    EIGHTH_SEMESTER("EIGHTH_SEMESTER"),
    ;
    private final String semesterNo;

    Semester(String semesterNo) {
        this.semesterNo = semesterNo;
    }
    public String getSemesterNo() {
        return semesterNo;
    }
}
