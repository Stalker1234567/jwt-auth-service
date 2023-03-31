package com.robo.harvexsolo.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.robo.harvexsolo.service.JwtValidationInterface;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtValidatorImpl implements JwtValidationInterface {

    private JwtServiceImpl jwtService;

    @Override
    public boolean isTokenValid(String accessToken, UserDetails userDetails) {
        final String username = jwtService.extractUsername(accessToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(accessToken);
    }

    @Override
    public boolean isTokenExpired(String accessToken) {
        return jwtService.extractExpired(accessToken).before(new Date());
    }
}
