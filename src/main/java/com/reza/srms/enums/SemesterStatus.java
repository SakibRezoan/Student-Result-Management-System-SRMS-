package com.reza.srms.enums;


public enum SemesterStatus {
    DROPPED("Dropped"),
    FIRST_SEMESTER("First Semester"),
    SECOND_SEMESTER("Second Semester"),
    THIRD_SEMESTER("Third Semester"),
    FOURTH_SEMESTER("Fourth Semester"),
    FIFTH_SEMESTER("Fifth Semester"),
    SIXTH_SEMESTER("Sixth Semester"),
    SEVENTH_SEMESTER("Seventh Semester"),
    EIGHTH_SEMESTER("Eighth Semester"),
    PASSED("Passed");
    private final String label;

    SemesterStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
