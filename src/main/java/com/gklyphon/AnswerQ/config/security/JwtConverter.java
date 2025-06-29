package com.gklyphon.AnswerQ.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom converter that transforms a Jwt object into a {@link Authentication} authentication token.
 * It extracts user roles from the JWT claims and attaches additional information like email and username.
 *
 * @author JFCiscoHuerta
 * @since 2025/06/23
 */
public class JwtConverter implements Converter<Jwt, Authentication> {

    /**
     * Converts the given Jwt into a {@link Authentication} authentication token,
     * extracting roles and custom user information from claims.
     *
     * @param jwt the JWT token to convert
     * @return the corresponding CustomJwt authentication token
     */
    @Override
    public Authentication convert(Jwt jwt) {
        List<GrantedAuthority> grantedAuthorities = extractAuthorities(jwt);
        var customJwt = new Authentication(jwt, grantedAuthorities);
        customJwt.setEmail(jwt.getClaimAsString("email"));
        customJwt.setUsername(jwt.getClaimAsString("preferred_username"));
        return customJwt;
    }


    /**
     * Extracts granted authorities (roles) from the "realm_access" claim of the JWT.
     *
     * @param jwt the JWT token from which to extract roles
     * @return a list of {@link GrantedAuthority} based on roles found in the token
     */
    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        var result = new ArrayList<GrantedAuthority>();
        var realmAccess = jwt.getClaimAsMap("realm_access");

        if (realmAccess != null && realmAccess.get("roles") != null) {
            var roles = realmAccess.get("roles");
            if (roles instanceof List<?> i) {
                i.forEach(role -> {
                    result.add(new SimpleGrantedAuthority("ROLE_".concat((String) role)));
                });
            }
        }

        return result;
    }

}
