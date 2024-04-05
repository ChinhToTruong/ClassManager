package com.example.classmanager.service.impl;

import com.example.classmanager.dto.request.RoomRequest;
import com.example.classmanager.entity.Room;
import com.example.classmanager.entity.Student;
import com.example.classmanager.entity.Teacher;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.StudentRepository;
import com.example.classmanager.repository.TeacherRepository;
import com.example.classmanager.service.RoomService;
import com.example.classmanager.service.StudentService;
import com.example.classmanager.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Room createRoom(RoomRequest request) {
        // create new room

        // get user will be on room
        Set<Teacher> teachers = getTeachersFromId(request.getTeachersId());
        Set<Student> students = getStudentsFromId(request.getStudentsId());

        // check room exist
        if(roomRepository.findRoomByName(request.getName()).isPresent()){
            throw new CommonException("Room existed.");
        }

        return roomRepository.save(Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .teachers(teachers)
                .students(students)
                .build());


    }

    @Override
    public Room updateRoomById(Long id, RoomRequest request) {
        // update room info include: student, teacher, ...

        // check room existed on db
        Room room = getRoomById(id);
        if (room == null){
            throw new CommonException("Room not found");
        }


        // get list update user
        Set<Student> updateStudents = getStudentsFromId(request.getStudentsId());
        Set<Teacher> updateTeachers = getTeachersFromId(request.getTeachersId());

        // do update
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setStudents(updateStudents);
        room.setTeachers(updateTeachers);
        
        return roomRepository.save(room);
    }

    private Set<Student> getStudentsFromId(Set<Long> ids){
        Set<Student> list = new HashSet<>();

        if (ids != null){
            ids
                    .stream().map(st ->  studentRepository
                            .findById(st)
                            .orElseThrow(() -> new CommonException("Teacher not found." + st)))
                    .forEach(list::add);
        }

        return list;

    }

    private Set<Teacher> getTeachersFromId(Set<Long> ids){
        Set<Teacher> list = new HashSet<>();

        if (ids != null){
            ids
                    .stream().map(t ->  teacherRepository
                            .findById(t)
                            .orElseThrow(() -> new CommonException("Teacher not found." + t)))
                    .forEach(list::add);
        }

        return list;
    }

}
