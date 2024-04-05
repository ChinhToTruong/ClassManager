package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.BaseInfoUserRequest;
import com.example.classmanager.entity.Teacher;
import com.example.classmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    // get all teacher in list
    List<Teacher> getAllTeachers();

    // filter by name
    Teacher getTeacherById(Long id);

    // update info teacher include rooms and info
    Teacher updateInfoTeacherById(Long id, BaseInfoUserRequest request);

    // delete teacher from db
    void deleteTeacherById(Long id);

    void addStudentToRoom(Long teacherId, Long roomId);

    void removeStudentFromRoom(Long teacherId, Long roomId);
}
