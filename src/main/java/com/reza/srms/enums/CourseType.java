package com.reza.srms.enums;

public enum CourseType {
    COMPULSORY("Compulsory"),
    OPTIONAL("Optional");
    private final String label;

    CourseType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
