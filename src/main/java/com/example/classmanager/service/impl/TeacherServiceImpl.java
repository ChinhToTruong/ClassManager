package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.*;
import com.example.classmanager.entity.enums.ERole;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoleRepository;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.TeacherRepository;
import com.example.classmanager.repository.UserRepository;
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
    public List<UserDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream()
                .map(teacher -> UserDto.builder()
                        .id(teacher.getId())
                        .rooms(teacher.getRooms().stream().map(Room::getName).collect(Collectors.toSet()))
                        .dateOfBirth(teacher.getDateOfBirth())
                        .gender(teacher.getGender())
                        .fullName(teacher.getFullName())
                        .roles(teacher.getUser().getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()))
                        .build())
                .toList();
    }


    @Override
    public List<UserDto> getTeacherByName(String name) {
        List<Teacher> teachers = teacherRepository.findTeachersByFullName(name);
        return teachers.stream().map(this::teacherToUserDto).toList();
    }

    @Override
    public Optional<UserDto> updateTeacher(UserDto userDto) {
        // input not include user(username, password and role) and id
        // allow update others info not include user( above )

        Teacher teacher = teacherRepository.findByUsername(userDto.getUsername()).orElseThrow(()-> new CommonException("User not found."));

        Set<Room> rooms = userDto.getRooms()
                .stream().map(room -> roomRepository.findRoomByName(room)
                        .orElseThrow(()-> new CommonException("Room not found."))
                ).collect(Collectors.toSet());

        teacher.setRooms(rooms);
        teacher.setFullName(userDto.getFullName());
        teacher.setGender(userDto.getGender());
        teacher.setDateOfBirth(userDto.getDateOfBirth());

        // update teacher
        teacherRepository.save(teacher);
        return Optional.of(userDto);
    }

    @Override
    public void deleteTeacher(String username) {
        var teacher = teacherRepository.findByUsername(username).orElseThrow(()-> new CommonException("Username not found."));
        teacherRepository.delete(teacher);
    }


    private Teacher userDtoToTeacher(UserDto userDto){
        Teacher teacher = new Teacher();

        // get roles from dto
        Set<Role> roles = userDto.getRoles()
                .stream().map(role -> roleRepository.findByName(ERole
                        .valueOf(role)).orElseThrow(()-> new CommonException("Role not exist "+ role))
                ).collect(Collectors.toSet());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .roles(roles)
                .build();

        // get rooms from dto
        Set<Room> rooms = new HashSet<>();
        userDto.getRooms().forEach(room -> rooms.add(roomRepository.findRoomByName(room).orElseThrow(()-> new CommonException("Room not found " + room))));

        teacher.setRooms(rooms);
        teacher.setGender(userDto.getGender());
        teacher.setDateOfBirth(userDto.getDateOfBirth());
        teacher.setFullName(userDto.getFullName());
        teacher.setUser(user);



        return teacherRepository.save(teacher);
    }

    private UserDto teacherToUserDto(Teacher teacher){

        Set<String> roles = teacher.getUser().getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        Set<String> rooms = teacher.getRooms().stream().map(Room::getName).collect(Collectors.toSet());

        return UserDto.builder()
                .id(teacher.getId())
                .dateOfBirth(teacher.getDateOfBirth())
                .gender(teacher.getGender())
                .fullName(teacher.getFullName())
                .rooms(rooms)
                .roles(roles)
                .build();
    }
}
