package com.example.classmanager.repository;

import com.example.classmanager.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "select r from Room r where r.name = ?1")
    Optional<Room> findRoomByName(String name);

    @Query(value = "select r from Room r where r.id = ?1")
    Optional<Room> findRoomById(Long id);

}
