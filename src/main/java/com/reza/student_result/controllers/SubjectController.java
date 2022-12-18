package com.reza.student_result.controllers;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.response.SubjectResponse;
import com.reza.student_result.services.impl.SubjectServiceImpl;
import com.reza.student_result.validators.SubjectValidator;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.reza.student_result.constant.MessageConstants.SUBJECT_SAVE;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import javax.validation.Valid;
import static com.reza.student_result.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/subject")
public class SubjectController {
    private final SubjectServiceImpl subjectServiceImpl;
    private final SubjectValidator validator;

    @PostMapping("/save")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody SubjectRequest request, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, request, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Subject subject = subjectServiceImpl.save(request);
        return ok(success(SubjectResponse.from(subject), SUBJECT_SAVE).getJson());
    }
}
