package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.UsernamePasswordRequest;
import com.example.classmanager.dto.request.UsernamePasswordRoleRequest;
import com.example.classmanager.dto.response.JwtResponse;
import com.example.classmanager.entity.*;
import com.example.classmanager.entity.enums.ERole;
import com.example.classmanager.entity.enums.EToken;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.*;
import com.example.classmanager.security.customUser.CustomUserDetails;
import com.example.classmanager.security.jwt.JwtUtils;
import com.example.classmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final SummaryRepository summaryRepository;

    @Override
    public User register(UsernamePasswordRoleRequest request) {
        // create new account

        // check account exist
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()){
            throw new UsernameNotFoundException("Username already existed!");
        }


        // get roles
        var roles = request.getRoles().stream().map(r -> roleRepository.findByName(ERole.valueOf(r)).orElseThrow(()-> new CommonException("Role not found. Please create new role."))).collect(Collectors.toSet());

        // create new entity without info link to account
        var user = userRepository.save(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                        .roles(roles)
                .build());

        roles.forEach(r -> {
            saveUserEntity(user, r);
        });

        return user;
    }

    @Override
    public JwtResponse login(UsernamePasswordRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // get user and generate token
        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Incorrect username or password. Please try again."));
        var jwtToken = jwtUtils.generateToken(new HashMap<>(), CustomUserDetails.buildUserDetails(user));
        var refreshToken = jwtUtils.generateRefreshToken(CustomUserDetails.buildUserDetails(user));

        // check token is valid
        revokeAllUserTokens(user);

        // save token, time login to user
        saveUserToken(user, jwtToken);
        saveUserSummary(user);

        return JwtResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public void updateUserInfo(Long id, UsernamePasswordRoleRequest request) {

        // get user from request
        User user = userRepository.findById(id).orElseThrow();

        // set info update
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var updateRoles = request.getRoles().stream().map(r ->  roleRepository.findByName(ERole.valueOf(r)).orElseThrow(() ->  new CommonException("Role not exist" + r))).collect(Collectors.toSet());

        // save user
        userRepository.save(updateRoleUser(updateRoles, user));
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(EToken.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveUserSummary(User user) {
        var summary = Summary.builder().user(user).build();
        summaryRepository.save(summary);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId().intValue());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserEntity(User user, Role role){
        if (role.getName().equals(ERole.ROLE_TEACHER)){
            teacherRepository.save(Teacher.builder()
                    .user(user)
                    .build());
        }
        else if(role.getName().equals(ERole.ROLE_STUDENT)){
            studentRepository.save(Student.builder()
                    .user(user)
                    .build());
        }
    }

    private User updateRoleUser(Set<Role> roles, User user){
        Set<Role> existRole = user.getRoles();

        Set<Role> updateRoles = roles.stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(()-> new CommonException("Role not found "  +role))).collect(Collectors.toSet());

        for(Role r : updateRoles){
            // check user have this role or not
            if (existRole.contains(r)){
                throw new CommonException("User have this role " + r.getName());
            }

            // add if not have role
            existRole.add(r);
            user.setRoles(existRole);
        }

        return user;
    }

}
