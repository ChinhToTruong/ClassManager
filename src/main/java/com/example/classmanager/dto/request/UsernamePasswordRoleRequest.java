package com.example.classmanager.dto.request;

import com.example.classmanager.entity.enums.ERole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UsernamePasswordRoleRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Set<String> roles;
}
