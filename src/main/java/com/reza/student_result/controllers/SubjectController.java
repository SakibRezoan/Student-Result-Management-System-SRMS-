package com.reza.student_result.controllers;

import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.services.impl.SubjectServiceImpl;
import com.reza.student_result.validators.SubjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/subject")
public class SubjectController {
    private final SubjectServiceImpl service;
    private final SubjectValidator validator;

    @PostMapping("/save")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody SubjectRequest request, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, request, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body()

        }
        return null;
    }
}
