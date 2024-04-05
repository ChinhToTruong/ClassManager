package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Role;
import com.example.classmanager.entity.User;
import com.example.classmanager.entity.enums.ERole;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.RoleRepository;
import com.example.classmanager.repository.UserRepository;
import com.example.classmanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> createRole(UserDto userDto) {
        // create new roles
        Set<Role> roleName = userDto.getRoles().stream().map(String::toUpperCase).map(r -> Role.builder().name(ERole.valueOf(r)).build()).collect(Collectors.toSet());

        // check roles exist
        Set<Boolean> checkRole = userDto.getRoles().stream().map(role -> roleRepository.findByName(ERole.valueOf(role)).isPresent()).collect(Collectors.toSet());
        boolean containsTrue = false;
        for (Boolean value : checkRole) {
            if (value) {
                containsTrue = true;
                break;
            }
        }

        // if not found roles do creation
        if (containsTrue){
            throw new CommonException("Some roles exist.");
        }
        roleRepository.saveAll(roleName);
        return roleName;
    }

    @Override
    public void deleteRole(Long id) {

    }

    @Override
    public Role findByName(String name) {
        return null;
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        return null;
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        return null;
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        return null;
    }
}
