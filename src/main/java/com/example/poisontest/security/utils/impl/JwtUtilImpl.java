package com.example.poisontest.security.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.poisontest.models.User;
import com.example.poisontest.security.details.UserDetailsImpl;
import com.example.poisontest.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DratkOMG
 * @created 00:22 - 05/04/2024
 */
@Component
public class JwtUtilImpl implements JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRES_TIME = 60 * 1000 * 60 * 24;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Map<String, String> generateTokens(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String jwtAccessToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_TIME))
                .sign(algorithm);

        Map<String, String> jwtToken = new HashMap<>();
        jwtToken.put("jwtAccessToken", jwtAccessToken);

        return jwtToken;
    }

    @Override
    public Authentication buildAuthentication(String token) {
        String username = parse(token);

        UserDetails userDetails = new UserDetailsImpl(User.builder()
                .username(username)
                .build());

        return new UsernamePasswordAuthenticationToken(userDetails, null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private String parse(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String username = decodedJWT.getSubject();

        return username;

    }
}
