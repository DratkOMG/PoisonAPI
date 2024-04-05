package com.example.poisontest.security.utils.impl;

import com.example.poisontest.security.utils.AuthorizationHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * @author DratkOMG
 * @created 00:12 - 05/04/2024
 */
@Component
public class AuthorizationHeaderUtilImpl implements AuthorizationHeaderUtil {

    private static final String AUTHORIZATION_HEADER_NAME = "AUTHORIZATION";

    private static final String BEARER = "Bearer ";

    @Override
    public boolean hasAuthorizationToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME);

        return header != null && header.startsWith(BEARER);
    }

    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME);

        return authorizationHeader.substring(BEARER.length());
    }
}
