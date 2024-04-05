package com.example.poisontest.security.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author DratkOMG
 * @created 00:12 - 05/04/2024
 */
public interface AuthorizationHeaderUtil {
    boolean hasAuthorizationToken(HttpServletRequest httpServletRequest);

    String getToken(HttpServletRequest httpServletRequest);
}
