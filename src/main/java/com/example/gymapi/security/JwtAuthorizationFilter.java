package com.example.gymapi.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final HandlerExceptionResolver resolver;

    public JwtAuthorizationFilter(JwtService jwtService,@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.resolver = resolver;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!this.isHeaderValid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwtService.verifyAccessToken(authHeader.substring(7))
                    .ifPresent(decodedJWT -> {
                        var username = decodedJWT.getSubject();
                        var authorities = decodedJWT.getClaim("roles").asList(String.class).stream()
                                .map(SimpleGrantedAuthority::new).toList();
                        var authToken = new UsernamePasswordAuthenticationToken(
                                username, null, authorities
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    });
        } catch (JWTVerificationException exception){
            resolver.resolveException(request, response, null, exception);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderValid(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }
}
