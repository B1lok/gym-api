package com.example.gymapi.security;


import com.example.gymapi.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthorizationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers("/sign-up").anonymous();
                    auth.requestMatchers("/sign-in").anonymous();
                    auth.requestMatchers(HttpMethod.GET, ALLOWED_GET_METHODS).permitAll();
                    auth.requestMatchers("/gym/user/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.COACH.name());
                    auth.requestMatchers("/gym/subscriptions/**").hasRole(Role.ADMIN.name());
                    auth.requestMatchers("/gym/coach/**").hasRole(Role.COACH.name());
                    auth.requestMatchers("/gym/admin/**").hasRole(Role.ADMIN.name());
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    private static final String[] ALLOWED_GET_METHODS = {
            "/gym/subscriptions",
            "/gym/coach/getAll",
    };

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
