package com.ecagataydogan.akbankservice.converter;

import com.ecagataydogan.akbankservice.dto.request.ApplicationRequest;
import com.ecagataydogan.akbankservice.dto.response.ApplicationResponse;
import com.ecagataydogan.akbankservice.entity.Application;
import com.ecagataydogan.akbankservice.enums.ApplicationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationConverter {

    public Application toApplication(ApplicationRequest request) {
        return Application.builder()
                .userId(request.getUserId())
                .createDate(LocalDateTime.now())
                .applicationStatus(ApplicationStatus.INITIAL)
                .build();
    }

    public ApplicationResponse toResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .userId(application.getUserId())
                .createDate(application.getCreateDate())
                .applicationStatus(application.getApplicationStatus())
                .build();
    }

}
