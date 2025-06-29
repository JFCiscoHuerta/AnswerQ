package com.gklyphon.AnswerQ.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/register"};
    private final String[] AUTHENTICATED_ENDPOINTS = {"/v1/answers/**","/v1/forms/**", "/v1/question/**"
        , "/v1/user-answers/**"};

    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                (auths) -> auths
                        .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtConverter()))
        ).sessionManagement(manager -> manager.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)
        ).cors( (cors) -> cors
                .configurationSource(corsConfiguration())
        ).csrf(AbstractHttpConfigurer::disable)
        .build();
    }

    @Bean
    Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {return new JwtConverter();}

    @Bean
    CorsConfigurationSource corsConfiguration() {

        final List<String> ALLOWED_METHODS = List.of("GET","POST","PUT","DELETE");

        CorsConfiguration configuration = new CorsConfiguration();
        //Temporal origin
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
