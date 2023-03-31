package com.robo.harvexsolo.service;

import com.robo.harvexsolo.dto.AuthRequestDto;
import com.robo.harvexsolo.dto.AuthResponseDto;
import com.robo.harvexsolo.dto.RegistrationRequestDto;

public interface AuthServiceInterface {
    AuthResponseDto register(RegistrationRequestDto request);
    AuthResponseDto authenticate(AuthRequestDto request);
}
