package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.LoginRequest;
import com.example.classmanager.dto.request.RegisterRequest;
import com.example.classmanager.dto.response.JwtResponse;
import com.example.classmanager.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
    JwtResponse login(LoginRequest request);

    void updateUserInfo(UserDto request);
}
