package com.example.poisontest.security.config;

import com.example.poisontest.security.filter.JwtTokenAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author DratkOMG
 * @created 23:34 - 04/04/2024
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Component
public class JwtTokenSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(JwtTokenAuthorizationFilter authorizationFilter,
                                                   HttpSecurity httpSecurity) throws Exception {

        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.POST, "/api/v1/users/sign-up").permitAll()
                            .anyRequest().authenticated();
                });

        httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
