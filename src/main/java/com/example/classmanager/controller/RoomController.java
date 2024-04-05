package com.example.classmanager.controller;

import com.example.classmanager.dto.request.BaseRoomInfoRequest;
import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createRoom(@RequestBody BaseRoomInfoRequest request){
        return ApiResponse.build()
                .withData(roomService.createRoom(request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoomById(@PathVariable("id") Long id, @RequestBody BaseRoomInfoRequest request){
        return ApiResponse.build()
                .withData(roomService.updateRoomById(id, request))
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable("id") Long id){

        roomService.deleteRoomById(id);
        return ApiResponse.build()
                .withMessage("Success")
                .withHttpStatus(HttpStatus.OK)
                .withSuccess(true)
                .toEntity();
    }
}
