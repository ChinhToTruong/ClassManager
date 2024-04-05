package com.example.classmanager.controller;


import com.example.classmanager.dto.request.UsernamePasswordRequest;
import com.example.classmanager.dto.request.UsernamePasswordRoleRequest;
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
    public ResponseEntity<?> register(@RequestBody UsernamePasswordRoleRequest request){
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Sign up successfully!")
                .withData(userService.register(request))
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsernamePasswordRequest request){
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Login successfully.")
                .withData(userService.login(request))
                .toEntity();
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody UsernamePasswordRoleRequest request){
        userService.updateUserInfo(id, request);
        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Update successfully.")
                .toEntity();
    }
}
