package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.UsernamePasswordRequest;
import com.example.classmanager.dto.request.UsernamePasswordRoleRequest;
import com.example.classmanager.dto.response.JwtResponse;
import com.example.classmanager.entity.User;

public interface AuthService {
    User register(UsernamePasswordRoleRequest request);
    JwtResponse login(UsernamePasswordRequest request);

    void updateUserInfo(Long id, UsernamePasswordRoleRequest request);
}
