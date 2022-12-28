package com.reza.student_result.enums;

public enum StudentStatus {
    DROPPED("DROPPED"),
    FIRST("FIRST"),
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    FIFTH("FIFTH"),
    SIXTH("SIXTH"),
    SEVENTH("SEVENTH"),
    EIGHTH("EIGHTH"),
    PASSED("PASSED")

    ;
    private final String semesterStatus;

    public String getSemesterStatus() {
        return semesterStatus;
    }

    StudentStatus(String semesterStatus) {
        this.semesterStatus = semesterStatus;
    }
}
