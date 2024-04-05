package com.example.classmanager.service;

import com.example.classmanager.dto.request.BaseRoomInfoRequest;
import com.example.classmanager.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRoom();

    Room getRoomById(Long id);

    Room createRoom(BaseRoomInfoRequest request);

    Room updateRoomById(Long id, BaseRoomInfoRequest request);

    void deleteRoomById(Long id);
}
