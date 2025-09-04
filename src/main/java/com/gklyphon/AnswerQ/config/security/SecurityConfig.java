package com.gklyphon.AnswerQ.config.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    @Bean
    UserDetailsService actuatorUsers(PasswordEncoder encoder, Environment environment) {
        String prometheusPwd = environment.getRequiredProperty("ACTUATOR_PASSWORD");
        String prometheusUsername = environment.getRequiredProperty("ACTUATOR_USERNAME");

        UserDetails prometheusUser = User.withUsername(prometheusUsername)
                .password(encoder.encode(prometheusPwd))
                .roles("ACTUATOR")
                .build();

        return new InMemoryUserDetailsManager(prometheusUser);
    }

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
     * Configures Actuator security filter chain.
     *
     * @param httpSecurity The HTTP security builder
     * @return Configured SecurityFilterChain
     * @throws Exception If configuration fails
     */
    @Bean
    @Order(1)
    SecurityFilterChain actuatorSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(EndpointRequest.to("health", "prometheus")).hasRole("ACTUATOR")
                        .anyRequest().denyAll()
                )
                .httpBasic(httpBasic -> {})
                .build();
    }

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity The HTTP Security builder
     * @return Configured SecurityFilterChain
     * @throws Exception If configuration fails
     */
    @Bean
    @Order(2)
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
