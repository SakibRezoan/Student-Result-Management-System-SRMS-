package com.reza.student_result.controllers;

import com.reza.student_result.entities.Subject;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.SubjectRequest;
import com.reza.student_result.response.SubjectResponse;
import com.reza.student_result.services.impl.SubjectServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.utils.PaginatedResponse;
import com.reza.student_result.validators.SubjectValidator;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import static com.reza.student_result.constant.MessageConstants.SUBJECT_SAVE;
import static com.reza.student_result.constant.MessageConstants.SUBJECT_UPDATE;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reza.student_result.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/subject")
public class SubjectController {
    private final SubjectServiceImpl subjectServiceImpl;
    private final SubjectValidator validator;

    private final CommonDataHelper helper;

    @GetMapping("/list")
    public ResponseEntity<JSONObject> getList (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "subjectName", defaultValue = "") String subjectName,
            @RequestParam(value = "subjectTypeId", defaultValue = "") Long subjectTypeId
    ) {

        helper.setPageSize(page,size);

        PaginatedResponse paginatedResponse = new PaginatedResponse();

        Map <String, Object> subjectMapSearchResult = subjectServiceImpl.searchSubject(subjectName, subjectTypeId,
                                                                            page, size, sortBy);
        List<Subject> responses = (List<Subject>) subjectMapSearchResult.get("lists");

        List<SubjectResponse> customResponse = responses.stream().
                            map(SubjectResponse::from).
                            collect(Collectors.toList());
        helper.getCommonData(page, size, subjectMapSearchResult, paginatedResponse, customResponse);

        return ok(paginatedSuccess(paginatedResponse).getJson());
    }

    @PostMapping("/save")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody SubjectRequest request, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, request, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Subject subject = subjectServiceImpl.save(request);
        return ok(success(SubjectResponse.from(subject), SUBJECT_SAVE).getJson());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {

        Optional<SubjectResponse> response = Optional.ofNullable(subjectServiceImpl.findById(id).map(SubjectResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }

    @PutMapping("/update")
    public ResponseEntity<JSONObject> update(@Valid @RequestBody SubjectRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        Subject subject = subjectServiceImpl.update(request);
        return ok(success(SubjectResponse.from(subject), SUBJECT_UPDATE).getJson());
    }





}
