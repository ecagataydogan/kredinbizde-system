package com.ecagataydogan.kredinbizdeservice.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressRequest {
    private String addressTitle;
    private String addressDescription;
    private String province;
}
