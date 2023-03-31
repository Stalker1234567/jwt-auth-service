package com.robo.harvexsolo.service.impl;

import com.robo.harvexsolo.service.JwtServiceInterface;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtServiceInterface {

    private JwtGeneratedImpl jwtGeneratedToken;

    @Override
    public String extractUsername(String accessToken) {
        return extractClaim(accessToken, Claims::getSubject);
    }

    @Override
    public Claims extractAllClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtGeneratedToken.getAccessAuthKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    @Override
    public Date extractExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    @Override
    public <J> J extractClaim(String accessToken, Function<Claims, J> claimsJFunction) {
        final Claims claims = extractAllClaims(accessToken);
        return claimsJFunction.apply(claims);
    }
}
