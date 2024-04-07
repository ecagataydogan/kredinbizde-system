package com.ecagataydogan.kredinbizdeservice.service;


import com.ecagataydogan.kredinbizdeservice.converter.UserConverter;
import com.ecagataydogan.kredinbizdeservice.dto.request.CreateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.UpdateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.UserResponse;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.exception.UserAlreadyExistException;
import com.ecagataydogan.kredinbizdeservice.exception.UserNotFoundException;
import com.ecagataydogan.kredinbizdeservice.producer.NotificationProducer;
import com.ecagataydogan.kredinbizdeservice.producer.dto.NotificationDTO;
import com.ecagataydogan.kredinbizdeservice.producer.enums.NotificationType;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final NotificationProducer notificationProducer;


    @Transactional(rollbackOn = UserAlreadyExistException.class)
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new UserAlreadyExistException("email already exists");
        }
        User toSave = userConverter.toUser(createUserRequest);
        User savedUser = userRepository.save(toSave);
        log.info("user saved");
        notificationProducer.sendNotification(prepareNotificationDTO(NotificationType.EMAIL, savedUser.getEmail()));
        return userConverter.toResponse(savedUser);
    }


    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::toResponse).toList();
    }

    public UserResponse getUserById(Long userId) {
        Optional<User> optionalUser =  userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            log.info("take from db ");
            return userConverter.toResponse(optionalUser.get());

        }
        //Exception will occur
        throw new UserNotFoundException("user not found");
    }

    @Transactional(rollbackOn = UserNotFoundException.class)
    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser =  userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            updateUserFields(foundUser,updateUserRequest);
            return userConverter.toResponse(userRepository.save(foundUser));
        }
        //Exception will occur
        throw new UserNotFoundException("user not found");

    }


    private void updateUserFields(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getSurname() != null) {
            user.setSurname(updateUserRequest.getSurname());
        }
        if (updateUserRequest.getBirthDate() != null) {
            user.setBirthDate(updateUserRequest.getBirthDate());
        }
        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }
        if (updateUserRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if (updateUserRequest.getAddress() != null) {
            user.setAddress(updateUserRequest.getAddress());
        }
    }

    private NotificationDTO prepareNotificationDTO(NotificationType notificationType, String email) {
        return NotificationDTO.builder()
                .message("user saved")
                .notificationType(notificationType)
                .email(email)
                .build();
    }
}
