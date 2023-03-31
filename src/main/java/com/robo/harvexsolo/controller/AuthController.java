package com.robo.harvexsolo.controller;

import com.robo.harvexsolo.dto.AuthRequestDto;
import com.robo.harvexsolo.dto.AuthResponseDto;
import com.robo.harvexsolo.dto.RegistrationRequestDto;
import com.robo.harvexsolo.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegistrationRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
