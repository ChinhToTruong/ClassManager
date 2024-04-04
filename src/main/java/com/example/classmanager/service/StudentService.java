package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;

import java.util.List;

public interface StudentService {

    List<UserDto> getStudentByRoomId(Long id);

    UserDto updateStudentById(UserDto userDto);
}
