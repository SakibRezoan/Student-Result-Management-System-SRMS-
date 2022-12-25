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
    private final String semester;

    Semester(String semester) {
        this.semester = semester;
    }
    public String getSemester() {
        return semester;
    }
}
