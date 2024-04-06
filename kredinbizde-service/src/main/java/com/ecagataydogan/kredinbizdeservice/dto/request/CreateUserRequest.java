package com.ecagataydogan.kredinbizdeservice.dto.request;

import com.ecagataydogan.kredinbizdeservice.entity.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateUserRequest implements Serializable {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phoneNumber;
    private AddressRequest address;
}
