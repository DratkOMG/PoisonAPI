package com.example.poisontest.security.utils;

import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * @author DratkOMG
 * @created 00:21 - 05/04/2024
 */
public interface JwtUtil {

    Map<String, String> generateTokens(String subject);

    Authentication buildAuthentication(String token);
}
