package com.example.classmanager.controller;


import com.example.classmanager.dto.UserDto;
import com.example.classmanager.entity.Role;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody UserDto userDto){
        // this method just get roles in userDto
        try{
            roleService.createRole(userDto);
            return ResponseEntity.ok("New role created successfully!");
        }catch(CommonException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());

        }
    }
}
