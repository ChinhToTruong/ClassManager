package com.example.classmanager.service;

import com.example.classmanager.dto.request.RoomRequest;
import com.example.classmanager.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRoom();

    Room getRoomById(Long id);

    Room createRoom(RoomRequest request);

    Room updateRoomById(Long id, RoomRequest request);
}
