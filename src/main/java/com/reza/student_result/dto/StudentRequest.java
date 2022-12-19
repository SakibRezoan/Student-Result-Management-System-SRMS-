package com.reza.student_result.dto;

import com.reza.student_result.entities.Enclosure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor (staticName = "build")
@NoArgsConstructor
public class StudentRequest {

    @NotNull(message = "roll shouldn't be null")
    @Min(value = 100000, message = "roll no should be within 100000 - 999999")
    @Max(value = 999999, message = "roll no should be within 100000 - 999999")
    private Long roll;

    @NotNull(message = "student name shouldn't be null")
    private String name;

    @Email(message = "please enter valid email address")
    private String email;

    @Pattern(regexp = "A+|A|A-|B|C|D|F", message = "Please enter valid result")
    private String result;

    private List<Enclosure> enclosures;
}
