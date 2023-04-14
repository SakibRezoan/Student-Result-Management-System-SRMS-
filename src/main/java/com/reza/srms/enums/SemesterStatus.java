package com.reza.srms.enums;


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
    private final String label;

    SemesterStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
