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
    public void createRole(UserDto userDto) {
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
    }

    @Override
    public void deleteRole(Long id) {

    }

    @Override
    public Role findByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow(() -> new CommonException("Role not found" +  name));
    }

}
