package com.ecagataydogan.kredinbizdeservice.service;

import com.ecagataydogan.kredinbizdeservice.client.AkbankServiceClient;
import com.ecagataydogan.kredinbizdeservice.client.dto.request.AkbankApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.converter.ApplicationConverter;
import com.ecagataydogan.kredinbizdeservice.dto.request.ApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.ApplicationResponse;
import com.ecagataydogan.kredinbizdeservice.dto.response.UserResponse;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import com.ecagataydogan.kredinbizdeservice.exception.ApplicationNotFoundException;
import com.ecagataydogan.kredinbizdeservice.exception.UserNotFoundException;
import com.ecagataydogan.kredinbizdeservice.repository.ApplicationRepository;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ApplicationConverter applicationConverter;
    private final AkbankServiceClient akbankServiceClient;


    @Transactional(rollbackOn = UserNotFoundException.class)
    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {
        Optional<User> optionalUser = userRepository.findById(applicationRequest.getUserId());
        if (optionalUser.isPresent()) {
            Application toSave = applicationConverter.toApplication(applicationRequest,optionalUser.get());
            toSave.setApplicationStatus(ApplicationStatus.INITIAL);
            applicationRepository.save(toSave);
            ApplicationResponse akbankApplicationResponse = akbankServiceClient.createApplication(prepareAkbankApplicationRequest(optionalUser.get()));
            return applicationConverter.toResponse(toSave);
        }
        //error will occur
        throw new UserNotFoundException("user not found");
    }

    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(applicationConverter::toResponse)
                .toList();
    }

    public ApplicationResponse getApplicationById(Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            return applicationConverter.toResponse(optionalApplication.get());
        }
        //Error will occur
        throw new ApplicationNotFoundException("application not found");
    }

    private AkbankApplicationRequest prepareAkbankApplicationRequest(User user) {
        AkbankApplicationRequest applicationRequest = new AkbankApplicationRequest();
        applicationRequest.setUserId(user.getId());
        return applicationRequest;
    }
}
