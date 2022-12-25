package com.reza.student_result.enums;

public enum StudentStatus {
    REGULAR("REGULAR"),
    DROPPED_OUT("DROPPED_OUT"),
    PASSED("PASSED")

    ;
    private final String status;

    public String getStatus() {
        return status;
    }

    StudentStatus(String status) {
        this.status = status;
    }
}
