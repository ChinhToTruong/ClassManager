package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.BaseInfoUserRequest;
import com.example.classmanager.entity.Room;
import com.example.classmanager.entity.Student;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoomRepository;
import com.example.classmanager.repository.StudentRepository;
import com.example.classmanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    @Override
    public Set<Student> getStudentByRoomId(Long id) {
        return roomRepository.findRoomById(id).orElseThrow().getStudents();
    }

    @Override
    public Student updateStudentById(Long id, BaseInfoUserRequest request) {
        // update user

        // get user
        Student student = studentRepository.findStudentById(id).orElseThrow(() -> new CommonException("Student not found."));

        // do update
        student.setGender(request.getGender());
        student.setEmail(request.getEmail());
        student.setFullName(request.getFullName());

        // save info
        studentRepository.save(student);

        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudentById(Long id) {
        var student  = studentRepository.findStudentById(id).orElseThrow(() -> new CommonException("Student not found " + id));
        studentRepository.delete(student);
    }

    @Override
    public void addStudentToRoom(Long studentId, Long roomId) {

        // get user from userId, room from roomId
        var student  = studentRepository.findStudentById(studentId).orElseThrow(() -> new CommonException("Student not found " + studentId));
        var room = roomRepository.findRoomById(roomId).orElseThrow(() -> new CommonException("Room not found " + roomId));

        // check student in room. if not in room then add room
        if (student.getRooms().contains(room)){
            throw new CommonException("Student " + student.getFullName() + " in this room " + room.getName());
        }
        student.getRooms().add(room);

        studentRepository.save(student);
    }

    @Override
    public void removeStudentFromRoom(Long studentId, Long roomId) {

        // get user from userId, room from roomId
        var student  = studentRepository.findStudentById(studentId).orElseThrow(() -> new CommonException("Student not found " + studentId));
        var room = roomRepository.findRoomById(roomId).orElseThrow(() -> new CommonException("Room not found " + roomId));

        // check student in room. if in room then remove room
        if (!student.getRooms().contains(room)){
            throw new CommonException("Student " + student.getFullName() + "not in this room " + room.getName());
        }
        student.getRooms().remove(room);

        studentRepository.save(student);
    }
}
