package com.reza.student_result.controllers;

import com.reza.student_result.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/result")
public class ResultController {
    private final StudentService studentService;


}
