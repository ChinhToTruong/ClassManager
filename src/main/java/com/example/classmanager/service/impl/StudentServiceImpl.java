package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Room;
import com.example.classmanager.entity.Student;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoleRepository;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.StudentRepository;
import com.example.classmanager.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<UserDto> getStudentByRoomId(Long id) {
        Set<Student> students = roomRepository.findRoomById(id).orElseThrow().getStudents();
        return students.stream().map(s -> UserDto.builder()
                        .fullName(s.getFullName())
                        .gender(s.getGender())
                        .id(s.getId())
                        .dateOfBirth(s.getDateOfBirth())
                        .build())
                .toList();
    }

    @Override
    public UserDto updateStudentById(UserDto userDto) {
        Student student = studentRepository.findStudentById(userDto.getId()).orElseThrow(() -> new CommonException("Student not found."));

        Set<Room> rooms = userDto.getRooms()
                .stream().map(r -> roomRepository
                        .findRoomByName(r).orElseThrow(() -> new CommonException("Room not found " + r)))
                .collect(Collectors.toSet());

        student.setRooms(rooms);
        student.setGender(userDto.getGender());
        student.setEmail(userDto.getEmail());
        student.setFullName(userDto.getFullName());

        studentRepository.save(student);

        return userDto;
    }
}
