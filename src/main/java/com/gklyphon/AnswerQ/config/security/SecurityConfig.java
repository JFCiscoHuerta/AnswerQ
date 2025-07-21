package com.gklyphon.AnswerQ.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration class that defines the security policies.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private final String[] PUBLIC_ENDPOINTS = {"/auth/**"};
    private final String[] AUTHENTICATED_ENDPOINTS = {"/v1/answers/**","/v1/forms/**", "/v1/question/**"
        , "/v1/user-answers/**"};

    /**
     * Creates and configures the authentication manager.
     *
     * @param configuration The authentication configuration
     * @return Configured AuthenticationManager
     * @throws Exception If configuration fails
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity The HTTP Security builder
     * @return Configured SecurityFilterChain
     * @throws Exception If configuration fails
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                (auths) -> auths
                        .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
        ).sessionManagement(manager -> manager.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)
        ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
        ).cors( (cors) -> cors
                .configurationSource(corsConfiguration())
        ).csrf(AbstractHttpConfigurer::disable)
        .build();
    }

    /**
     * Configures CORS settings.
     *
     * @return Configured CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfiguration() {

        final List<String> ALLOWED_ORIGINS = List.of("http://localhost:8081");
        final List<String> ALLOWED_METHODS = List.of("GET","POST","PUT","DELETE");
        final List<String> ALLOWED_HEADERS = List.of("Authorization, Content-Type");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
