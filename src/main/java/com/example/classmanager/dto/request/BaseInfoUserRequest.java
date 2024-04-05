package com.example.classmanager.dto.request;

import com.example.classmanager.entity.enums.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class BaseInfoUserRequest {
    private String fullName;

    private LocalDateTime dateOfBirth;

    private EGender gender;

    private String email;
}
