package com.example.classmanager.service;

import com.example.classmanager.dto.request.BaseInfoUserRequest;
import com.example.classmanager.entity.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {

    Set<Student> getStudentByRoomId(Long id);

    Student updateStudentById(Long id, BaseInfoUserRequest request);

    List<Student> getAllStudents();

    void deleteStudentById(Long id);

    void addStudentToRoom(Long studentId, Long roomId);

    void removeStudentFromRoom(Long studentId, Long roomId);

}
