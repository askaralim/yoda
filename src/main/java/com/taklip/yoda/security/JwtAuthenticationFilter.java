package com.taklip.yoda.security;

import com.taklip.yoda.model.User;
import com.taklip.yoda.service.JwtService;
import com.taklip.yoda.service.LoginUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final LoginUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Skip JWT authentication for certain paths
        String requestPath = request.getRequestURI();
        if (shouldSkipJwtAuthentication(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.info("JwtAuthenticationFilter UserDetails: {}", userDetails);
                User user = null;
                if (userDetails instanceof User) {
                    user = (User) userDetails;
                }

                log.info("JwtAuthenticationFilter User: {}", user);

                if (jwtService.isTokenValid(jwt, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            log.error("JWT authentication failed: {}", e);
            // Continue without authentication - let other filters handle it
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipJwtAuthentication(String requestPath) {
        // Skip JWT for session-based authentication paths
        return requestPath.startsWith("/login") ||
                requestPath.startsWith("/logout") ||
                requestPath.startsWith("/controlpanel") ||
                requestPath.startsWith("/portal") ||
                requestPath.startsWith("/file") ||
                requestPath.startsWith("/403") ||
                requestPath.startsWith("/404") ||
                requestPath.startsWith("/500") ||
                requestPath.startsWith("/actuator") ||
                requestPath.startsWith("/swagger") ||
                requestPath.startsWith("/v3/api-docs");
    }
}
