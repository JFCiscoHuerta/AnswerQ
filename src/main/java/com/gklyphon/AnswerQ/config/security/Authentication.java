package com.gklyphon.AnswerQ.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;


/**
 * Custom JWT authenticator that extends {@link JwtAuthenticationToken}
 * to include additional user information such as username and email.
 *
 * @author JFCiscoHuerta
 * @since 2025/04/23
 */
public class Authentication extends JwtAuthenticationToken {

    private String username;
    private String email;


    public Authentication(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
