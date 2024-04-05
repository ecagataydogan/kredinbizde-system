package com.ecagataydogan.akbankservice.dto.response;

import com.ecagataydogan.akbankservice.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplicationResponse {

    private Long id;
    private Long userId;
    private LocalDateTime createDate;
    private ApplicationStatus applicationStatus;
}
