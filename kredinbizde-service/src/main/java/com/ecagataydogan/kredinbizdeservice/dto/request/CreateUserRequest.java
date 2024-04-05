package com.ecagataydogan.kredinbizdeservice.dto.request;

import com.ecagataydogan.kredinbizdeservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequest {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;

}
