package com.example.classmanager.controller;

import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/room/{id}")
    public ResponseEntity<?> getStudentsByRoomId(@PathVariable("id") Long id){
        return ApiResponse.build()
                .withData(studentService.getStudentByRoomId(id))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

}
