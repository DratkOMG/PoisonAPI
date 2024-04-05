package com.example.poisontest.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.poisontest.security.utils.AuthorizationHeaderUtil;
import com.example.poisontest.security.utils.JwtUtil;
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

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
            filterChain.doFilter(request, response);
        } else {
            if (authorizationHeaderUtil.hasAuthorizationToken(request)) {
                String token = authorizationHeaderUtil.getToken(request);

                try {
                    Authentication authentication = jwtUtil.buildAuthentication(token);
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
