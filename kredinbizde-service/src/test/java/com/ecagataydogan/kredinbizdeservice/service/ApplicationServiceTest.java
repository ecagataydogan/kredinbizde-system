package com.ecagataydogan.kredinbizdeservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecagataydogan.kredinbizdeservice.client.AkbankServiceClient;
import com.ecagataydogan.kredinbizdeservice.converter.ApplicationConverter;
import com.ecagataydogan.kredinbizdeservice.dto.request.ApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.ApplicationResponse;
import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import com.ecagataydogan.kredinbizdeservice.exception.UserNotFoundException;
import com.ecagataydogan.kredinbizdeservice.repository.ApplicationRepository;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationConverter applicationConverter;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private AkbankServiceClient akbankServiceClient;

    @InjectMocks
    private ApplicationService applicationService;




    @Test
    public void should_create_application_successfully() {

        ApplicationRequest applicationRequest = prepareApplicationRequest();
        User user = prepareUser();
        Application application = prepareApplication();
        ApplicationResponse expectedResponse = prepareApplicationResponse();

        when(userRepository.findById(applicationRequest.getUserId())).thenReturn(Optional.of(user));
        when(applicationConverter.toApplication(applicationRequest, user)).thenReturn(application);
        when(applicationConverter.toResponse(any())).thenReturn(expectedResponse);
        when(akbankServiceClient.createApplication(any())).thenReturn(expectedResponse);

        ApplicationResponse actualResponse = applicationService.createApplication(applicationRequest);

        verify(applicationRepository, times(1)).save(application);
        verify(akbankServiceClient, times(1)).createApplication(any());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void should_throw_user_not_found_exception_when_user_not_found() {
        ApplicationRequest applicationRequest = prepareApplicationRequest();
        applicationRequest.setUserId(5L);

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> applicationService.createApplication(applicationRequest));
    }

    @Test
    public void should_return_all_applications(){
        List<Application> mockApplications = prepareMockApplications();

        when(applicationRepository.findAll()).thenReturn(mockApplications);

        List<ApplicationResponse> applicationResponses = applicationService.getAllApplications();

        assertNotNull(applicationResponses);
        assertEquals(mockApplications.size(), applicationResponses.size());

    }

    @Test
    public void should_return_application_by_id(){
        Application application = prepareApplication();
        ApplicationResponse applicationResponse = prepareApplicationResponse();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        when(applicationConverter.toResponse(application)).thenReturn(applicationResponse);

        ApplicationResponse actualResponse = applicationService.getApplicationById(1L);

        assertNotNull(actualResponse);
        assertEquals(applicationResponse, actualResponse);

    }


    // Helper methods to prepare test data
    private ApplicationRequest prepareApplicationRequest() {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setUserId(1L);
        return applicationRequest;
    }

    private User prepareUser() {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setSurname("test");
        user.setBirthDate(LocalDate.now());
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setPhoneNumber("55555555");
        user.setIsActive(true);
        user.setAddress(new Address());
        user.setApplications(new ArrayList<>());
        return user;
    }

    private Application prepareApplication() {
        Application application = new Application();
        application.setUser(prepareUser());
        application.setId(1L);
        application.setApplicationStatus(ApplicationStatus.INITIAL);
        return application;
    }

    private ApplicationResponse prepareApplicationResponse() {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setId(1L);
        applicationResponse.setApplicationStatus(ApplicationStatus.INITIAL);
        applicationResponse.setUserId(1L);
        return applicationResponse;
    }

    private ApplicationResponse prepareExpectedResponse(Application application) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setApplicationStatus(application.getApplicationStatus());
        applicationResponse.setUserId(application.getUser().getId());
        applicationResponse.setId(application.getId());
        return applicationResponse;
    }

    private List<Application> prepareMockApplications() {
        List<Application> mockApplications = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            Application application = new Application();
            application.setId(i);
            application.setApplicationStatus(ApplicationStatus.INITIAL);
            application.setUser(new User());
            mockApplications.add(application);
        }
        return mockApplications;
    }
}

