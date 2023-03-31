package com.robo.harvexsolo.service;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtServiceInterface {
    String extractUsername(String token);
    Claims extractAllClaims(String token);
    Date extractExpired(String jwt);
    <J> J extractClaim(String token, Function<Claims, J> claimsJFunction);
}
