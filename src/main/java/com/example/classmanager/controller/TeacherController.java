package com.example.classmanager.controller;

import com.example.classmanager.dto.request.BaseInfoUserRequest;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("id") Long id){

        teacherService.deleteTeacherById(id);

        return ApiResponse.build()
                .withSuccess(true)
                .withHttpStatus(HttpStatus.OK)
                .withMessage("Delete successfully.")
                .toEntity();
    }


    @PutMapping("/update/add-room")
    public ResponseEntity<?> addStudentToRoom(@RequestParam("stdId") Long studentId, @RequestParam("rId") Long roomId){

        teacherService.addStudentToRoom(studentId, roomId);

        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/remove-room")
    public ResponseEntity<?> removeStudentToRoom(@RequestParam("stdId") Long studentId, @RequestParam("rId") Long roomId){

        teacherService.removeStudentFromRoom(studentId, roomId);

        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudentInfo(@PathVariable("id") Long id, @RequestBody BaseInfoUserRequest request){
        return ApiResponse.build()
                .withData(teacherService.updateInfoTeacherById(id, request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }


}
