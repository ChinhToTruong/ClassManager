package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.BaseInfoUserRequest;
import com.example.classmanager.entity.*;
import com.example.classmanager.entity.enums.ERole;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoleRepository;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.TeacherRepository;
import com.example.classmanager.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<Teacher> getAllTeachers() {

        return teacherRepository.findAll();
    }


    @Override
    public Teacher getTeacherById(Long id) {

        return teacherRepository.findById(id).orElseThrow();
    }

    @Override
    public Teacher updateInfoTeacherById(Long id, BaseInfoUserRequest request) {
        // input not include user(username, password and role) and id
        // allow update others info not include user( above )

        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new CommonException("User not found " + id));


        teacher.setFullName(request.getFullName());
        teacher.setGender(request.getGender());
        teacher.setDateOfBirth(request.getDateOfBirth());

        // update teacher
        return teacherRepository.save(teacher);

    }

    @Override
    public void deleteTeacherById(Long id) {
        var teacher = teacherRepository.findById(id).orElseThrow(()-> new CommonException("Username not found."));
        teacherRepository.delete(teacher);
    }

    @Override
    public void addStudentToRoom(Long teacherId, Long roomId) {
        // get user from userId, room from roomId
        var teacher  = teacherRepository.findById(teacherId).orElseThrow(() -> new CommonException("Student not found " + teacherId));
        var room = roomRepository.findRoomById(roomId).orElseThrow(() -> new CommonException("Room not found " + roomId));

        // check student in room. if not in room then add room
        if (teacher.getRooms().contains(room)){
            throw new CommonException("Student " + teacher.getFullName() + " in this room " + room.getName());
        }
        teacher.getRooms().add(room);

        teacherRepository.save(teacher);
    }

    @Override
    public void removeStudentFromRoom(Long teacherId, Long roomId) {
        // get user from userId, room from roomId
        var teacher  = teacherRepository.findById(teacherId).orElseThrow(() -> new CommonException("Student not found " + teacherId));
        var room = roomRepository.findRoomById(roomId).orElseThrow(() -> new CommonException("Room not found " + roomId));

        // check student in room. if not in room then add room
        if (!teacher.getRooms().contains(room)){
            throw new CommonException("Student " + teacher.getFullName() + " not in this room " + room.getName());
        }
        teacher.getRooms().remove(room);

        teacherRepository.save(teacher);
    }
}
