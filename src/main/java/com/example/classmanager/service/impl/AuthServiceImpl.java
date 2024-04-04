package com.example.classmanager.service.impl;

import com.example.classmanager.dto.UserDto;
import com.example.classmanager.dto.request.LoginRequest;
import com.example.classmanager.dto.request.RegisterRequest;
import com.example.classmanager.dto.response.JwtResponse;
import com.example.classmanager.entity.*;
import com.example.classmanager.entity.enums.ERole;
import com.example.classmanager.entity.enums.EToken;
import com.example.classmanager.exception.custom.CommonException;
import com.example.classmanager.repository.*;
import com.example.classmanager.security.customUser.CustomUserDetails;
import com.example.classmanager.security.jwt.JwtUtils;
import com.example.classmanager.service.AuthService;
import com.example.classmanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public User register(RegisterRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()){
            throw new UsernameNotFoundException("Username already existed!");
        }

        Set<Role> roles = new  HashSet<>();

        var role = roleRepository.findByName(ERole.valueOf(request.getRole())).orElseThrow(()-> new CommonException("Role not found. Please create new role.    "));

        roles.add(role);



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
    public JwtResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Incorrect username or password. Please try again."));

        var jwtToken = jwtUtils.generateToken(new HashMap<>(), CustomUserDetails.buildUserDetails(user));
        var refreshToken = jwtUtils.generateRefreshToken(CustomUserDetails.buildUserDetails(user));

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        saveUserSummary(user);

        return JwtResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public void updateUserInfo(UserDto request) {

        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<ERole> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        if (roles.contains(ERole.ROLE_TEACHER)){
            var teacher = teacherRepository.findById(user.getTeacher().getId()).orElseThrow();

            teacher.setFullName(request.getFullName());
            teacher.setGender(request.getGender());
            teacher.setDateOfBirth(request.getDateOfBirth());

            teacherRepository.save(teacher);
        }
        else if (roles.contains(ERole.ROLE_STUDENT)){
            var st = studentRepository.findById(user.getStudent().getId()).orElseThrow();

            st.setFullName(request.getFullName());
            st.setGender(request.getGender());
            st.setDateOfBirth(request.getDateOfBirth());

            studentRepository.save(st);
        }

        userRepository.save(user);
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




}
