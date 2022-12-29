package com.reza.student_result.enums;


public enum SemesterStatus {
    DROPPED("DROPPED"),
    FIRST_SEMESTER("FIRST_SEMESTER"),
    SECOND_SEMESTER("SECOND_SEMESTER"),
    THIRD_SEMESTER("THIRD_SEMESTER"),
    FOURTH_SEMESTER("FOURTH_SEMESTER"),
    FIFTH_SEMESTER("FIFTH_SEMESTER"),
    SIXTH_SEMESTER("SIXTH_SEMESTER"),
    SEVENTH_SEMESTER("SEVENTH_SEMESTER"),
    EIGHTH_SEMESTER("EIGHTH_SEMESTER"),
    PASSED("PASSED")
    ;
    private final String semesterStatus;

    SemesterStatus(String semesterStatus) {
        this.semesterStatus = semesterStatus;
    }

    public String getSemesterStatus() {
        return semesterStatus;
    }
}
