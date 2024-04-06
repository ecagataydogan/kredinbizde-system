package com.ecagataydogan.kredinbizdeservice.converter;

import com.ecagataydogan.kredinbizdeservice.dto.request.CreateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.request.UpdateUserRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.UserResponse;
import com.ecagataydogan.kredinbizdeservice.entity.User;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserConverter {
    public User toUser(CreateUserRequest createUserRequest) {
        AddressConverter addressConverter = new AddressConverter();
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setSurname(createUserRequest.getSurname());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setAddress(addressConverter.toAddress(createUserRequest.getAddress()));
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setIsActive(true);
        user.setApplications(new ArrayList<>());
        return user;
    }

    public UserResponse toResponse(User user) {
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

    public User toUser(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(updateUserRequest.getPassword());
        user.setAddress(updateUserRequest.getAddress());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        return user;
    }
}
