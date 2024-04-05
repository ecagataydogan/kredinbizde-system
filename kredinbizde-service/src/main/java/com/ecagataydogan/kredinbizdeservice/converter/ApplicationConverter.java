package com.ecagataydogan.kredinbizdeservice.converter;

import com.ecagataydogan.kredinbizdeservice.dto.request.ApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.CreateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.ApplicationResponse;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConverter {

    public Application toApplication(ApplicationRequest applicationRequest, User user) {
        Application application = new Application();
        application.setUser(user);
        return application;
    }

    public ApplicationResponse toResponse(Application application) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setApplicationStatus(application.getApplicationStatus());
        applicationResponse.setUserId(application.getUser().getId());
        applicationResponse.setId(application.getId());
        return applicationResponse;
    }
}
