package com.reza.srms.enums;


public enum Semester {
    FIRST_SEMESTER("First_Semester"),
    SECOND_SEMESTER("Second_Semester"),
    THIRD_SEMESTER("Third_Semester"),
    FOURTH_SEMESTER("Fourth_Semester"),
    FIFTH_SEMESTER("Fifth_Semester"),
    SIXTH_SEMESTER("Sixth_Semester"),
    SEVENTH_SEMESTER("Seventh_Semester"),
    EIGHTH_SEMESTER("Eighth_Semester");
    private final String label;

    Semester(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
