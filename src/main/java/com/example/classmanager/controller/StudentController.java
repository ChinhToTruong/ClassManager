package com.example.classmanager.controller;

import com.example.classmanager.dto.request.BaseInfoUserRequest;
import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStudents(){
        return ApiResponse.build()
                .withData(studentService.getAllStudents())
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudentInfo(@PathVariable("id") Long id, @RequestBody BaseInfoUserRequest request){
        return ApiResponse.build()
                .withData(studentService.updateStudentById(id, request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("id") Long id){

        studentService.deleteStudentById(id);

        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/add-room")
    public ResponseEntity<?> addStudentToRoom(@RequestParam("stdId") Long studentId, @RequestParam("rId") Long roomId){

        studentService.addStudentToRoom(studentId, roomId);

        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/remove-room")
    public ResponseEntity<?> removeStudentToRoom(@RequestParam("stdId") Long studentId, @RequestParam("rId") Long roomId){

        studentService.removeStudentFromRoom(studentId, roomId);

        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }
}
