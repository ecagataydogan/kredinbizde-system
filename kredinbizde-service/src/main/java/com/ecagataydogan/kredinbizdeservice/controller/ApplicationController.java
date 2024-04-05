package com.ecagataydogan.kredinbizdeservice.controller;


import com.ecagataydogan.kredinbizdeservice.dto.request.ApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import com.ecagataydogan.kredinbizdeservice.repository.ApplicationRepository;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/applications")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;


    @PostMapping
    public Application save(@RequestBody ApplicationRequest applicationRequest) {

        Optional<User> user = userRepository.findById(applicationRequest.getUserId());
        Application application = new Application();
        if(user.isPresent()){
            User foundUser = user.get();
            application.setUser(foundUser);
            application.setApplicationStatus(ApplicationStatus.INITIAL);
            System.out.println("Buraya düştü");
            return applicationRepository.save(application);
        }
        return new Application();
    }

}
