package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Role;
import com.example.classmanager.entity.User;
import com.example.classmanager.entity.enums.ERole;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    void createRole(UserDto userDto);

    void deleteRole(Long id);
    Role findByName(ERole name);
}
