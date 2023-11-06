package com.example.gymapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gymapi.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Component
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;

    public String createJwtToken(User user){
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(secret));
    }

    public Optional<DecodedJWT> verifyAccessToken(String token) {
        return Optional.of(JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build().verify(token));
    }
}
