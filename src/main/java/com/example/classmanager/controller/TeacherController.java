package com.example.classmanager.controller;

import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTeachers(){
        return ApiResponse.build()
                .withSuccess(true)
                .withMessage("Success.")
                .withHttpStatus(HttpStatus.OK)
                .withData(teacherService.getAllTeachers())
                .toEntity();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeacher(@RequestBody String username){

        teacherService.deleteTeacher(username);

        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Delete successfully.")
                .toEntity();
    }


}
