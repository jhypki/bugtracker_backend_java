package com.example.bugtracker_backend.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtRequestFilter is a custom filter that extends OncePerRequestFilter to
 * handle JWT authentication.
 * It intercepts each request to extract and validate the JWT token from the
 * Authorization header.
 * If the token is valid, it sets the authentication in the
 * SecurityContextHolder.
 * 
 * Dependencies:
 * - JwtUtils: Utility class for handling JWT operations such as extracting
 * email and validating tokens.
 * - UserDetailsService: Service to load user-specific data.
 * 
 * Constructor:
 * - JwtRequestFilter(JwtUtils jwtUtils, @Lazy UserDetailsService
 * userDetailsService): Initializes the filter with the required dependencies.
 * 
 * Methods:
 * - doFilterInternal(HttpServletRequest request, HttpServletResponse response,
 * FilterChain chain):
 * Overrides the method to perform the JWT authentication. It extracts the token
 * from the Authorization header,
 * validates it, and sets the authentication in the SecurityContextHolder if the
 * token is valid.
 * 
 * Usage:
 * - This filter should be registered in the security configuration to be
 * applied to incoming requests.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtils jwtUtils, @Lazy UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtils.extractEmail(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (jwtUtils.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
