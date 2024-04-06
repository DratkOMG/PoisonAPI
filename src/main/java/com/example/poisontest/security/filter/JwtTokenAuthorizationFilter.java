package com.example.poisontest.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.poisontest.security.service.AuthorizationHeaderService;
import com.example.poisontest.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author DratkOMG
 * @created 00:15 - 05/04/2024
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_URL = "/api/v1/users/sign-up";

    private final AuthorizationHeaderService authorizationHeaderService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
            filterChain.doFilter(request, response);
        } else {
            if (authorizationHeaderService.hasAuthorizationToken(request)) {
                String token = authorizationHeaderService.getToken(request);

                try {
                    Authentication authentication = jwtService.buildAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    logger.error(e.getMessage());

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }

    }
}
