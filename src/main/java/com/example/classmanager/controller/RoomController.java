package com.example.classmanager.controller;

import com.example.classmanager.dto.request.RoomRequest;
import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.entity.Room;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRoom() {
        return ApiResponse.build()
                .withData(roomService.getAllRoom())
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") Long id) {
        return ApiResponse.build()
                .withData(roomService.getRoomById(id))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest request){
        return ApiResponse.build()
                .withData(roomService.createRoom(request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @RequestBody RoomRequest request){
        return ApiResponse.build()
                .withData(roomService.updateRoomById(id, request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

}
