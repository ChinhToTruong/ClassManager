package com.example.classmanager.controller;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.LoginRequest;
import com.example.classmanager.dto.request.RegisterRequest;
import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Sign up successfully!")
                .withData(userService.register(request))
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Login successfully.")
                .withData(userService.login(request))
                .toEntity();
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto request){
        userService.updateUserInfo(request);
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Update successfully.")
                .withData(request)
                .toEntity();
    }
}
