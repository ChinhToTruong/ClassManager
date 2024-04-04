package com.example.classmanager.dto;

import com.example.classmanager.entity.enums.EGender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
public class UserDto {
    private Long id;

    private String fullName;

    private LocalDateTime dateOfBirth;

    private EGender gender;

    private String email;

    private String username;

    private String password;

    private Set<String> roles;

    private Set<String> rooms;

}
