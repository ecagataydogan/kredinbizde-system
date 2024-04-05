package com.ecagataydogan.kredinbizdeservice.service;


import com.ecagataydogan.kredinbizdeservice.converter.UserConverter;
import com.ecagataydogan.kredinbizdeservice.dto.request.CreateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.UpdateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.UserResponse;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import com.ecagataydogan.kredinbizdeservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User toSave = userConverter.toUser(createUserRequest);
        toSave.setIsActive(true);
        toSave.setApplications(new ArrayList<>());
        return userConverter.toResponse(userRepository.save(toSave));
    }


    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::toResponse).toList();
    }

    public UserResponse getUserById(Long userId) {
        Optional<User> optionalUser =  userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return userConverter.toResponse(optionalUser.get());
        }
        //Exception will occur
        return new UserResponse();
    }

    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser =  userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            updateUserFields(foundUser,updateUserRequest);
            return userConverter.toResponse(userRepository.save(foundUser));
        }
        //Exception will occur
        return new UserResponse();


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

}
