package com.ecagataydogan.kredinbizdeservice.service;

import com.ecagataydogan.kredinbizdeservice.converter.UserConverter;
import com.ecagataydogan.kredinbizdeservice.dto.request.AddressRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.CreateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.UpdateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.UserResponse;
import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.exception.UserAlreadyExistException;
import com.ecagataydogan.kredinbizdeservice.exception.UserNotFoundException;
import com.ecagataydogan.kredinbizdeservice.producer.NotificationProducer;
import com.ecagataydogan.kredinbizdeservice.producer.dto.NotificationDTO;
import com.ecagataydogan.kredinbizdeservice.producer.enums.NotificationType;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private UserConverter userConverter;



    @Test
    public void should_create_user_successfully() {
        NotificationType notificationType = NotificationType.EMAIL;
        String email = prepareCreateUserRequest().getEmail();
        CreateUserRequest createUserRequest = prepareCreateUserRequest();
        User toSave = prepareToSaveUser(createUserRequest);
        User saved = prepareToSaveUser(createUserRequest);

        when(userConverter.toUser(Mockito.any(CreateUserRequest.class))).thenReturn(toSave);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(saved);
        when(userConverter.toResponse(Mockito.any(User.class))).thenReturn(toResponse(saved));


        UserResponse userResponse = userService.createUser(prepareCreateUserRequest());

        verify(userRepository,times(1)).save(Mockito.any());
        assertThat(userResponse.getName()).isEqualTo(saved.getName());
        assertThat(userResponse.getSurname()).isEqualTo(saved.getSurname());
        assertThat(userResponse.getPassword()).isEqualTo(saved.getPassword());
        assertThat(userResponse.getEmail()).isEqualTo(saved.getEmail());
        assertThat(userResponse.getIsActive()).isEqualTo(saved.getIsActive());
        assertThat(userResponse.getPhoneNumber()).isEqualTo(saved.getPhoneNumber());
        assertThat(userResponse.getBirthDate()).isEqualTo(saved.getBirthDate());

    }

    @Test
    public void should_return_all_users() {
        List<User> users = prepareAllUsers();

        when(userRepository.findAll()).thenReturn(users);
        when(userConverter.toResponse(Mockito.any(User.class))).thenReturn(toResponse(createDummyUser()));

        List<UserResponse> userResponses = userService.getAllUsers();

        assertThat(userResponses.size()).isEqualTo(users.size());


    }

    @Test
    public void should_return_user_by_id() {
        User user = createDummyUser();


        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userConverter.toResponse(Mockito.any(User.class))).thenReturn(toResponse(user));


        UserResponse userResponse = userService.getUserById(1L);


        assertThat(userResponse.getName()).isEqualTo(user.getName());
        assertThat(userResponse.getSurname()).isEqualTo(user.getSurname());
        assertThat(userResponse.getPassword()).isEqualTo(user.getPassword());
        assertThat(userResponse.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponse.getIsActive()).isEqualTo(user.getIsActive());
        assertThat(userResponse.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userResponse.getBirthDate()).isEqualTo(user.getBirthDate());

    }


    @Test
    public void should_return_user_not_found_exception_when_user_not_found_by_id() {

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
    }

    @Test
    public void should_return_updated_user() {
        Long userId = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        User foundUser = createDummyUser();
        User updatedUser = createDummyUser();
        UserResponse expectedResponse = toResponse(createDummyUser());

        when(userRepository.findById(userId)).thenReturn(Optional.of(foundUser));
        when(userRepository.save(foundUser)).thenReturn(updatedUser);
        when(userConverter.toResponse(updatedUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.updateUser(userId, updateUserRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(foundUser);
        verify(userConverter, times(1)).toResponse(updatedUser);
    }

    @Test
    public void should_throw_email_already_exist_exception() {
        String existingEmail = "existing@example.com";
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(existingEmail);

        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(createUserRequest));

        verify(userRepository, never()).save(any());
        verify(notificationProducer, never()).sendNotification(any());
    }

    private CreateUserRequest prepareCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("test");
        createUserRequest.setSurname("test");
        createUserRequest.setPassword("password");
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setAddress(new AddressRequest());
        createUserRequest.setBirthDate(LocalDate.now());
        createUserRequest.setPhoneNumber("555555555");
        return createUserRequest;
    }

    private User prepareToSaveUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setSurname(createUserRequest.getSurname());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
//        user.setAddress(createUserRequest.getAddress());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        return user;
    }

    private UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setApplications(user.getApplications());
        userResponse.setAddress(user.getAddress());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setEmail(user.getEmail());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setPassword(user.getPassword());
        userResponse.setIsActive(user.getIsActive());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        return userResponse;
    }

    private NotificationDTO prepareNotificationDTO(NotificationType notificationType, String email) {
        return NotificationDTO.builder()
                .message("user kaydedildi.")
                .notificationType(notificationType)
                .email(email)
                .build();
    }

    private List<User> prepareAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(createDummyUser());
        users.add(createDummyUser());
        return users;
    }

    private User createDummyUser() {
        Address address = new Address();
        address.setId(1L);
        address.setAddressDescription("123 Main Street");
        address.setAddressTitle("Anytown");
        address.setProvince("USA");

        List<Application> applications = new ArrayList<>();

        return new User(
                1L,
                "test",
                "test",
                LocalDate.of(1990, 5, 15),
                "test@example.com",
                "password123",
                "55555555",
                true,
                address,
                applications
        );
    }

}
