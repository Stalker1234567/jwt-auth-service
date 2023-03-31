package com.robo.harvexsolo.service.impl;

import com.robo.harvexsolo.service.JwtGenerateInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtGeneratedImpl implements JwtGenerateInterface {

    @Value("${access.token}")
    private String accessToken;

//    @Value("${refresh.token}")
//    private String refreshToken;


    @Override
    public String generateAccessToken(Map<String, Object> accessMapToken, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(accessMapToken)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(ZonedDateTime.now().withNano(0).toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
                .signWith(getAccessAuthKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    @Override
    public Key getAccessAuthKey() {
        byte[] byteAccessKey = Decoders.BASE64.decode(accessToken);
        return Keys.hmacShaKeyFor(byteAccessKey);
    }

}
