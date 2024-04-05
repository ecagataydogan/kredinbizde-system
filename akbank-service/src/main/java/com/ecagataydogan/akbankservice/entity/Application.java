package com.ecagataydogan.akbankservice.entity;

import com.ecagataydogan.akbankservice.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;
}

