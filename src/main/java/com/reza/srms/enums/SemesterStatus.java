package com.reza.srms.enums;
public enum SemesterStatus {
    DROPPED("Dropped"),
    PASSED("Passed"),
    RUNNING("Running"),
    FAILED("Failed");
    private final String label;

    SemesterStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
