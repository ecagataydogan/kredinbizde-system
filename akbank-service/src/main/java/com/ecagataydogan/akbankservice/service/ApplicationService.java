package com.ecagataydogan.akbankservice.service;

import com.ecagataydogan.akbankservice.converter.ApplicationConverter;
import com.ecagataydogan.akbankservice.dto.request.ApplicationRequest;
import com.ecagataydogan.akbankservice.dto.response.ApplicationResponse;
import com.ecagataydogan.akbankservice.entity.Application;
import com.ecagataydogan.akbankservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository ;

    private final ApplicationConverter applicationConverter;

    public ApplicationResponse createApplication(ApplicationRequest request) {
        Application application = applicationConverter.toApplication(request);
        return applicationConverter.toResponse(applicationRepository.save(application));
    }

    public List<ApplicationResponse> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .map(applicationConverter::toResponse)
                .toList();

    }
}
