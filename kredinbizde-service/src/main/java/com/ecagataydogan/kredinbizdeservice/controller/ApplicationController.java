package com.ecagataydogan.kredinbizdeservice.controller;


import com.ecagataydogan.kredinbizdeservice.dto.request.ApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.ApplicationResponse;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import com.ecagataydogan.kredinbizdeservice.repository.ApplicationRepository;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import com.ecagataydogan.kredinbizdeservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/applications")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        return new ResponseEntity<>(applicationService.createApplication(applicationRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return new ResponseEntity<>(applicationService.getAllApplications(),HttpStatus.OK);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable Long applicationId) {
        return new ResponseEntity<>(applicationService.getApplicationById(applicationId),HttpStatus.OK);
    }



}
