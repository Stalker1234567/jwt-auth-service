package com.robo.harvexsolo.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtValidationInterface {
    boolean isTokenValid(String accessToken, UserDetails userDetails);
    boolean isTokenExpired(String accessToken);
}
