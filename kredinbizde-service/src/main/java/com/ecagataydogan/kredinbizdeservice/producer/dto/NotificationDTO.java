package com.ecagataydogan.kredinbizdeservice.producer.dto;

import com.ecagataydogan.kredinbizdeservice.producer.enums.NotificationType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private NotificationType notificationType;
    private String message;
    private String email;

}

