package com.example.classmanager.service;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Role;
import com.example.classmanager.entity.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getRoles();
    Set<Role> createRole(UserDto userDto);

    void deleteRole(Long id);
    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
