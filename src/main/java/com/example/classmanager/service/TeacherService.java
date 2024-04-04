package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Teacher;
import com.example.classmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    // get all teacher in list
    List<UserDto> getAllTeachers();

    // filter by name
    List<UserDto> getTeacherByName(String name);

    // update info teacher include rooms and info
    Optional<UserDto> updateTeacher(UserDto userDto);

    // delete teacher from db
    void deleteTeacher(String name);
}
