package com.example.classmanager.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernamePasswordRequest {
    private String username;
    private String password;
}
