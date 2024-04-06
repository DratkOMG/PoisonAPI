package com.example.poisontest.security.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author DratkOMG
 * @created 00:12 - 05/04/2024
 */
public interface AuthorizationHeaderService {
    boolean hasAuthorizationToken(HttpServletRequest httpServletRequest);

    String getToken(HttpServletRequest httpServletRequest);
}
