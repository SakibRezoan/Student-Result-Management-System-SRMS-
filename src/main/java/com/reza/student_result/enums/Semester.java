package com.reza.student_result.enums;

public enum Semester {
    FIRST("FIRST"),
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    FIFTH("FIFTH"),
    SIXTH("SIXTH"),
    SEVENTH("SEVENTH"),
    EIGHTH("EIGHTH"),
    ;
    private final String semesterNo;

    Semester(String semesterNo) {
        this.semesterNo = semesterNo;
    }
    public String getSemester() {
        return semesterNo;
    }
}
