package com.robo.harvexsolo.service.impl;

import com.robo.harvexsolo.dto.AuthRequestDto;
import com.robo.harvexsolo.dto.AuthResponseDto;
import com.robo.harvexsolo.dto.RegistrationRequestDto;
import com.robo.harvexsolo.model.Role;
import com.robo.harvexsolo.model.User;
import com.robo.harvexsolo.repository.UserRepository;
import com.robo.harvexsolo.service.AuthServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGeneratedImpl jwtGenerated;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponseDto register(RegistrationRequestDto request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
//                .birthday(request.getBirthday())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var JwtToken = jwtGenerated.generateAccessToken(user);
        log.info("Token is build");
        return AuthResponseDto.builder().accessToken(JwtToken).build();
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(NoSuchElementException::new);

        var JwtToken = jwtGenerated.generateAccessToken(user);
        return AuthResponseDto.builder().accessToken(JwtToken).build();
    }
}
