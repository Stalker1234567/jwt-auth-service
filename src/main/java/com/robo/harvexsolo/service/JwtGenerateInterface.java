package com.robo.harvexsolo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface JwtGenerateInterface {
    String generateAccessToken(Map<String, Object> mapToken, UserDetails userDetails);
    String generateAccessToken(UserDetails userDetails);
    Key getAccessAuthKey();
}
