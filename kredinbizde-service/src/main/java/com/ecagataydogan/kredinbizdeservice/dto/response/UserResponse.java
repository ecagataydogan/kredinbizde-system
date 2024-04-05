package com.ecagataydogan.kredinbizdeservice.dto.response;

import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.entity.Application;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isActive;
    private Address address;
    private List<Application> applications;
}
