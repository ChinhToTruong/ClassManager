package com.example.classmanager.service.impl;

import com.example.classmanager.dto.request.BaseRoomInfoRequest;
import com.example.classmanager.entity.Room;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.StudentRepository;
import com.example.classmanager.repository.TeacherRepository;
import com.example.classmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findRoomById(id).orElseThrow(()-> new CommonException("Room not found."));
    }

    @Override
    public Room createRoom(BaseRoomInfoRequest request) {
        // create new room

        // check room exist
        if(roomRepository.findRoomByName(request.getName()).isPresent()){
            throw new CommonException("Room existed.");
        }

        return roomRepository.save(Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());


    }

    @Override
    public Room updateRoomById(Long id, BaseRoomInfoRequest request) {
        // update room info include: student, teacher, ...

        // check room existed on db
        Room room = getRoomById(id);
        if (room == null){
            throw new CommonException("Room not found");
        }

        // do update
        room.setName(request.getName());
        room.setDescription(request.getDescription());

        return roomRepository.save(room);
    }

    @Override
    public void deleteRoomById(Long id) {
        Room room = getRoomById(id);
        if (room == null){
            throw new CommonException("Room not found");
        }
        roomRepository.delete(room);
    }


    //    private void updateStudentsToRoom(Set<Student> students, Room room){
//        if (students != null){
//            for (Student student : students){
//                student.getRooms().add(room);
//                studentRepository.save(student);
//            }
//        }
//        else {
//            throw new CommonException("List empty");
//        }
//
//
//    }
//
//    private void updateTeachersToRoom(Set<Teacher> teachers, Room room){
//
//        if (teachers != null){
//            for (Teacher teacher : teachers){
//                teacher.getRooms().add(room);
//                teacherRepository.save(teacher);
//            }
//        }
//        else {
//            throw new CommonException("List empty");
//        }
//
//    }
}
