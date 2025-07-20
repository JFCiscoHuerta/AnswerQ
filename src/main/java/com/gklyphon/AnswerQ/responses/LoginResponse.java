package com.gklyphon.AnswerQ.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response sent to the client after successful authentication.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
public class LoginResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("expiresIn")
    public long expiresIn;

    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
