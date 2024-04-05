package com.ecagataydogan.kredinbizdeservice.dto.response;

import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApplicationResponse {
    private Long id;
    private Long userId;
    private ApplicationStatus applicationStatus;

}
