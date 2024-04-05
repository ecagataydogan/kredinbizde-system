package com.ecagataydogan.kredinbizdeservice.client.dto.response;

import com.ecagataydogan.kredinbizdeservice.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AkbankApplicationResponse {

    private Long userId;
    private LocalDateTime createDate;
    private ApplicationStatus applicationStatus;
}
