package com.test.utils;

import com.auth0.jwt.JWT;
import com.test.dto.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JWTUtils {

    @Value("${security.jwt.expiry_seconds}")
    long expirySeconds;

    @Value("${security.jwt.auth_header}")
    String authHeader;

    @Value("${security.jwt.token_prefix}")
    String tokenPrefix;

    @Value("${security.jwt.secret}")
    String secret;

    public String authenticate(UserRequest userRequest){

        String token = JWT.create()
                .withSubject(userRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirySeconds))
                .sign(HMAC512(secret.getBytes()));

        token = tokenPrefix + " " + token;
        return token;
    }
}
